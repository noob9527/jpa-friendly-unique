package cn.staynoob.springdatajpahelper.utils

import com.google.common.base.CaseFormat

/**
 * convert lower underscore case to lower camel case
 */
fun convertCaseByDefault(input: String): String
        = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, input)

