package cn.staynoob.springdatajpahelper.autoconfigure

import cn.staynoob.springdatajpahelper.utils.JpaHelperPropertiesContainer
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ImportAutoConfiguration(JpaHelperAutoConfigureRepositoryImpl::class)
@EnableConfigurationProperties(JpaHelperProperties::class)
class JpaHelperAutoConfiguration {
    private val log = LoggerFactory.getLogger(JpaHelperAutoConfiguration::class.java)

    @Bean
    @ConditionalOnMissingBean
    fun jpaHelperPropertiesContainer(jpaHelperProperties: JpaHelperProperties): JpaHelperPropertiesContainer {
        log.info("jpaHelperProperties: $jpaHelperProperties")
        return JpaHelperPropertiesContainer.createInstance(jpaHelperProperties)
    }
}
