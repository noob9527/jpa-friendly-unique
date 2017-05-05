package cn.staynoob.springdatajpahelper.autoconfigure

import cn.staynoob.springdatajpahelper.utils.JpaHelperPropertiesContainer
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@EnableConfigurationProperties(JpaHelperProperties::class)
@Import(JpaHelperAutoConfigureRegistrar::class)
@AutoConfigureBefore(JpaRepositoriesAutoConfiguration::class)
class JpaHelperAutoConfiguration {
    private val log = LoggerFactory.getLogger(JpaHelperAutoConfiguration::class.java)

    @Bean
    fun jpaHelperPropertiesContainer(jpaHelperProperties: JpaHelperProperties): JpaHelperPropertiesContainer {
        log.info("jpaHelperProperties: $jpaHelperProperties")
        return JpaHelperPropertiesContainer.createInstance(jpaHelperProperties)
    }
}
