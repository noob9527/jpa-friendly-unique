package cn.staynoob.springdatajpahelper.service

import cn.staynoob.springdatajpahelper.utils.JpaHelperPropertiesContainer
import java.util.*
import javax.persistence.EntityManager

/**
 * http://frightanic.com/software-development/jpa-batch-inserts/
 */
class BatchService(
        val entityManager: EntityManager
) {
    fun <T> batch(
            entities: Iterable<T>,
            function: (T) -> T,
            isAtomic: Boolean = true
    ): Iterable<T> {
        val batchSize = JpaHelperPropertiesContainer.INSTANCE!!.jpaHelperProperties
                .batchSize
        val savedEntities = ArrayList<T>()
        var i = 0
        for (entity in entities) {
            try {
                savedEntities.add(function.invoke(entity))
                i++
            } catch (e: Exception) {
                if (!isAtomic) continue
                throw e
            }
            if (i % batchSize == 0) {
                entityManager.flush()
            }
        }
        return savedEntities
    }
}