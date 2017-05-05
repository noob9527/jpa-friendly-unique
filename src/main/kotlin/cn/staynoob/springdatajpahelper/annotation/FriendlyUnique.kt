package cn.staynoob.springdatajpahelper.annotation

@Target(AnnotationTarget.CLASS)
annotation class FriendlyUnique(
        val compositeUniques: Array<CompositeUnique>
)