package cn.staynoob.springdatajpahelper.annotation

/**
 * 声明多列唯一约束
 */
@Target
annotation class CompositeUnique(
        /**
         * 属性名（不是数据库列名）
         */
        val fields: Array<String>,
        /**
         * 决定是否要在保存数据前根据该注解做唯一检查
         */
        val isConstraint: Boolean = true,
        /**
         * 决定是否作为 exists 方法的判断条件
         */
        val isIdentity: Boolean = true,
        /**
         * exists 查询优先级
         */
        val queryPriority: Int = 0
)
