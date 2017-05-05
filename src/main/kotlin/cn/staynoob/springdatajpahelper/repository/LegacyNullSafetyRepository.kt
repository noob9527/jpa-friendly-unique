package cn.staynoob.springdatajpahelper.repository

import org.springframework.data.jpa.domain.Specification
import java.io.Serializable

/**
 * you won't need this in spring data 2+
 * see <https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.nullability.kotlin>
 */
interface LegacyNullSafetyRepository<T, in ID : Serializable> {
    fun getById(id: ID): T
    fun getOne(spec: Specification<T>): T
}