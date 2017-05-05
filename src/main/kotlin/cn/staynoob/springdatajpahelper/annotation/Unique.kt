package cn.staynoob.springdatajpahelper.annotation


/**
 * 在单个属性上标识唯一约束
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Unique(
        /**
         * 决定作为 exists 方法的判断条件
         */
        val isIdentity: Boolean = true,
        /**
         * 决定是否要在保存数据前根据该注解做唯一检查
         */
        val isConstraint: Boolean = true,
        /**
         * exists 查询优先级
         */
        val queryPriority: Int = 0
)

