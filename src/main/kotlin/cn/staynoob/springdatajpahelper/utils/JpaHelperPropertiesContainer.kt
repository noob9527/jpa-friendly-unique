package cn.staynoob.springdatajpahelper.utils

import cn.staynoob.springdatajpahelper.autoconfigure.JpaHelperProperties

/**
 * singleton properties container
 * this exists only because there isn't a simple way to inject property
 * to JpaHelperRepositoryImpl
 * refer to:RxJavaSampleKotlin/app/src/main/java/com/example/android/observability/persistence/UsersDatabase.kt
 * https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e
 */
class JpaHelperPropertiesContainer private constructor(
        jpaHelperProperties: JpaHelperProperties
) {
    var jpaHelperProperties: JpaHelperProperties = jpaHelperProperties
        private set

    companion object {
        @Volatile
        var INSTANCE: JpaHelperPropertiesContainer? = null
            private set

        fun createInstance(jpaHelperProperties: JpaHelperProperties): JpaHelperPropertiesContainer {
            return INSTANCE?.apply { this.jpaHelperProperties = jpaHelperProperties }
                    ?: synchronized(this) {
                INSTANCE ?: JpaHelperPropertiesContainer(jpaHelperProperties).also { INSTANCE = it }
            }
        }
    }
}