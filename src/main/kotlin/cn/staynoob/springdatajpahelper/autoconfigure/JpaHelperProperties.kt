package cn.staynoob.springdatajpahelper.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * this class have to be mutable, otherwise spring won't be
 * able to read related properties from configuration
 */
@ConfigurationProperties(prefix = "spring.data.jpa.helper")
data class JpaHelperProperties(
        var enableJpaUqAnnotation: Boolean = false,
        var jpaAnnotationConstraint: Boolean = true,
        var jpaAnnotationIdentity: Boolean = true,
        var batchSize: Int = 50
)
