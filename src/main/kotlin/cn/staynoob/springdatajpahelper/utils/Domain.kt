package cn.staynoob.springdatajpahelper.utils

typealias UQ = Set<String>
typealias PropertyEntry = Pair<String, Any?>

data class UniqueConstraint(
        val fields: UQ,
        val isConstraint: Boolean,
        val isIdentity: Boolean,
        val queryPriority: Int
)


