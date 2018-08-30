package cn.staynoob.springdatajpahelper.autoconfigure

import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@ConditionalOnProperty(prefix = "spring.data.jpa.helper", name = ["autoconfigure-repository-impl"], havingValue = "true", matchIfMissing = true)
@Import(JpaHelperAutoConfigureRegistrar::class)
@AutoConfigureBefore(JpaRepositoriesAutoConfiguration::class)
class JpaHelperAutoConfigureRepositoryImpl