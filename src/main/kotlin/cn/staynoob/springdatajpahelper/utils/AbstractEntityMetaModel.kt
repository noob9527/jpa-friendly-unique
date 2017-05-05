package cn.staynoob.springdatajpahelper.utils

import org.hibernate.Session
import javax.persistence.EntityManager

abstract class AbstractEntityMetaModel(
        protected val entity: Any
) {
    companion object {
        fun getMetaModelInstance(
                entity: Any,
                entityManager: EntityManager
        ): AbstractEntityMetaModel {
            val session = entityManager.unwrap(Session::class.java)
            return if (session == null) JpaMetaModel(entity, entityManager)
            else HibernateMetaModel(entity, session)
        }
    }

    /**
     * extract fieldValue by fieldName
     */
    abstract fun extractPropertyValue(fieldName: String): Any?

    /**
     * extract idFieldName idValue pair
     */
    abstract fun extractIdentifier(): PropertyEntry
}