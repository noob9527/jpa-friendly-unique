package cn.staynoob.springdatajpahelper.service

import cn.staynoob.springdatajpahelper.exception.DuplicatedEntityException
import cn.staynoob.springdatajpahelper.utils.*
import javax.persistence.EntityManager

@Suppress("LoopToCallChain")
class FriendlyUQService<in T : Any>(
        private val entityManager: EntityManager
) {

    fun <S : T> findByUQ(entity: S): S? {
        val metaModel = AbstractEntityMetaModel
                .getMetaModelInstance(entity, entityManager)

        val constraintList = extractIdentityUQ(entity::class)
        if (constraintList.isEmpty()) return null
        for (uniqueConstraint in constraintList) {
            val set: Set<PropertyEntry> = uniqueConstraint
                    .map { it to metaModel.extractPropertyValue(it) }
                    .toSet()
            findOneByUQ(entity, set)?.let { return it }
        }
        return null
    }

    fun validate(entity: T) {
        val metaModel = AbstractEntityMetaModel
                .getMetaModelInstance(entity, entityManager)

        val constraintList = extractValidationUQ(entity::class)
        if (constraintList.isEmpty()) return
        val identity = metaModel.extractIdentifier()

        for (uniqueConstraint in constraintList) {
            val set: Set<PropertyEntry> = uniqueConstraint
                    .map { it to metaModel.extractPropertyValue(it) }
                    .toSet()
            if (hasRecord(entity, identity, set)) {
                throw DuplicatedEntityException(entity::class, set.toMap())
            }
        }
    }

    private fun hasRecord(entity: T, identity: PropertyEntry, propertySet: Set<PropertyEntry>): Boolean {
        val cb = entityManager.criteriaBuilder
        val query = cb.createQuery(Long::class.java)
        val root = query.from(entity::class.java)

        val set = propertySet
                .map { cb.equal(root.get<T>(it.first), it.second) }
                .toMutableSet()
        identity.second
                ?.let { set.add(cb.notEqual(root.get<T>(identity.first), it)) }
        query.where(*set.toTypedArray())
        query.select(cb.count(root))
        val count = entityManager.createQuery(query)
                .singleResult
        return count > 0
    }

    private fun <S : T> findOneByUQ(entity: S, propertySet: Set<PropertyEntry>): S? {
        val cb = entityManager.criteriaBuilder
        val query = cb.createQuery(entity::class.java)
        val root = query.from(entity::class.java)
        val set = propertySet
                .map { cb.equal(root.get<T>(it.first), it.second) }
                .toSet()
        query.where(*set.toTypedArray())
        return entityManager.createQuery(query).singleResult
    }
}