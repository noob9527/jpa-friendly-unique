package cn.staynoob.springdatajpahelper.exception

import kotlin.reflect.KClass

class DuplicatedEntityException(
        val domainClass: KClass<*>,
        val fieldMap: Map<String, Any?>
) : RuntimeException(
        "duplicated entity of type ${domainClass.simpleName}"
)
