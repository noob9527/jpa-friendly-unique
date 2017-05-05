package cn.staynoob.springdatajpahelper.utils

import javax.persistence.EntityManager

class JpaMetaModel(
        entity: Any,
        private val entityManager: EntityManager
) : AbstractEntityMetaModel(entity) {

    private val metaModel = entityManager.metamodel

    private operator fun Any.get(key: String): Any?  = getPropertyValue(this, key)

    override fun extractPropertyValue(fieldName: String): Any? {
        return entity[fieldName]
    }

    override fun extractIdentifier(): PropertyEntry {
        val idName =  extractIdName()
        return idName to entity[idName]
    }

    private fun extractIdName(): String {
        return metaModel.entity(entity::class.java).singularAttributes
                .find { it.isId }?.name
                ?: throw Exception("Single @Id expected")
    }
}