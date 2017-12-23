package cn.staynoob.springdatajpahelper.annotation


/**
 * Singular Column Unique Constraint
 */
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class Unique(
        /**
         * Indicates if jpa-helper will use it in `exists` and `findByUQ` methods
         */
        val isIdentity: Boolean = true,
        /**
         * Indicates if jpa-helper will do uniqueCheck for you
         */
        val isConstraint: Boolean = true,
        /**
         * exists query priority
         * consider an entity has two unique key
         * class SomeEntity (@Unique val foo:String?=null, @Unique val bar:String?=null)
         * and db already have two records ("aaa", null), (null, "bbb")
         * then you try to findByUQ(SomeEntity("aaa", "bbb"))
         * this property indicated which entity you will be returned
         * findByUQ will use the larger priority UQ at first
         */
        val queryPriority: Int = 0
)

