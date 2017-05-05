package cn.staynoob.springdatajpahelper.utils

import org.hibernate.Session
import org.hibernate.engine.spi.SessionImplementor

class HibernateMetaModel(
        entity: Any,
        private val session: Session
) : AbstractEntityMetaModel(entity) {
    private val meta = session
            .sessionFactory.getClassMetadata(entity.javaClass)

    override fun extractPropertyValue(fieldName: String): Any? {
        return meta.getPropertyValue(entity, fieldName)
    }

    override fun extractIdentifier(): PropertyEntry {
        return meta.identifierPropertyName to meta.getIdentifier(entity, session as SessionImplementor)
    }
}