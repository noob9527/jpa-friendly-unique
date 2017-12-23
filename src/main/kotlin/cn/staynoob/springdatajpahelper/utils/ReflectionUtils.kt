package cn.staynoob.springdatajpahelper.utils

import cn.staynoob.springdatajpahelper.annotation.FriendlyUnique
import cn.staynoob.springdatajpahelper.annotation.Unique
import javax.persistence.Column
import javax.persistence.JoinColumn
import javax.persistence.Table
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

/**
 * @param clazz
 * @return MutableMap<fieldName to columnName>
 */
fun resolveColumns(clazz: KClass<*>): MutableMap<String, String> {
    val result = mutableMapOf<String, String>()
    for (field in clazz.java.declaredFields) {
        if (!field.isAnnotationPresent(Column::class.java) && !field.isAnnotationPresent(JoinColumn::class.java)) continue
        val columnName = field.getAnnotation(JoinColumn::class.java)?.name
                ?: field.getAnnotation(Column::class.java)?.name
        columnName?.let { result.put(field.name, columnName) }
    }
    return result
}

fun hasField(clazz: KClass<*>, fieldName: String): Boolean {
    return try {
        clazz.java.getDeclaredField(fieldName)
        true
    } catch (e: NoSuchFieldException) {
        false
    }
}

fun getPropertyValue(entity: Any, propertyName: String): Any? {
    val property = entity::class.memberProperties
            .find { it.name == propertyName }
    return property!!.getter.call(entity)
}

fun extractIdentityUQ(clazz: KClass<*>): List<UQ> {
    val properties = JpaHelperPropertiesContainer.INSTANCE!!
            .jpaHelperProperties
    val result = extractUniqueConstraint(clazz)
            .filter { it.isIdentity }
            .sortedBy { -it.queryPriority }
            .map { it.fields }
            .toList()
    return if (properties.enableJpaUqAnnotation && properties.jpaAnnotationIdentity) {
        result + extractUQByJpaAnnotation(clazz)
    } else {
        result
    }
}

fun extractValidationUQ(clazz: KClass<*>): Set<UQ> {
    val properties = JpaHelperPropertiesContainer.INSTANCE!!
            .jpaHelperProperties
    val result = extractUniqueConstraint(clazz)
            .filter { it.isConstraint }
            .map { it.fields }
            .toSet()
    return if (properties.enableJpaUqAnnotation && properties.jpaAnnotationConstraint) {
        result + extractUQByJpaAnnotation(clazz)
    } else {
        result
    }
}

/**
 * resolve @Unique and @CompositeUnique annotation
 */
fun extractUniqueConstraint(clazz: KClass<*>): Set<UniqueConstraint> {
    val result = mutableSetOf<UniqueConstraint>()

    // resolve @CompositeUnique
    clazz.findAnnotation<FriendlyUnique>()
            ?.let { friendlyUnique ->
                friendlyUnique.compositeUniques
                        .map {
                            UniqueConstraint(
                                    setOf(*it.fields),
                                    it.isConstraint,
                                    it.isIdentity,
                                    it.queryPriority
                            )
                        }
            }
            ?.also { result.addAll(it) }

    // resolve @Unique
    for (property in clazz.memberProperties) {
        val unique = property.findAnnotation<Unique>() ?: continue
        val uc = UniqueConstraint(
                setOf(property.name),
                unique.isConstraint,
                unique.isIdentity,
                unique.queryPriority
        )
        result.add(uc)
    }

    return result
}

/**
 * resolve @Column, @Table annotation
 */
fun extractUQByJpaAnnotation(clazz: KClass<*>)
        = (extractUQByColumnAnnotation(clazz) +
        extractUQByUniqueConstraintAnnotation(clazz))

private fun extractUQByColumnAnnotation(clazz: KClass<*>): Set<UQ> {
    return clazz.java.declaredFields.asSequence()
            .filter {
                it.isAnnotationPresent(Column::class.java)
                        && it.getAnnotation(Column::class.java).unique
            }
            .map { setOf(it.name) }
            .toSet()
}

private fun extractUQByUniqueConstraintAnnotation(clazz: KClass<*>): Set<UQ> {
    val table = clazz.findAnnotation<Table>() ?: return setOf()
    val cache = resolveColumns(clazz)
    val result = mutableSetOf<UQ>()

    for (uc in table.uniqueConstraints) {
        var isValid = true
        for (column in uc.columnNames) {
            if (cache.containsKey(column)) continue
            val fieldName = convertCaseByDefault(column)
            if (!hasField(clazz, fieldName)) {
                isValid = false //找不到对应的属性名
                break
            } else {
                cache.put(column, fieldName)
            }
        }
        if (!isValid) continue
        val fieldSet = uc.columnNames.map { cache[it]!! }.toSet()
        result.add(fieldSet)
    }
    return result
}