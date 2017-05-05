package cn.staynoob.springdatajpahelper.autoconfigure

import cn.staynoob.springdatajpahelper.repository.JpaHelperRepositoryImpl
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension
import org.springframework.data.repository.config.RepositoryConfigurationExtension

class JpaHelperAutoConfigureRegistrar : AbstractRepositoryConfigurationSourceSupport() {
    override fun getAnnotation(): Class<out Annotation> {
        return EnableJpaRepositories::class.java
    }

    override fun getConfiguration(): Class<*> {
        return EnableJpaRepositoriesConfiguration::class.java
    }

    override fun getRepositoryConfigurationExtension(): RepositoryConfigurationExtension {
        return JpaRepositoryConfigExtension()
    }

    @EnableJpaRepositories(repositoryBaseClass = JpaHelperRepositoryImpl::class)
    private class EnableJpaRepositoriesConfiguration
}