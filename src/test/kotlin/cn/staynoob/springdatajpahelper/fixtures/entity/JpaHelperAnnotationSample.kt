package cn.staynoob.springdatajpahelper.fixtures.entity

import cn.staynoob.springdatajpahelper.annotation.CompositeUnique
import cn.staynoob.springdatajpahelper.annotation.FriendlyUnique
import cn.staynoob.springdatajpahelper.annotation.Unique
import javax.persistence.Entity

@Entity
@FriendlyUnique(
        compositeUniques = [(CompositeUnique(fields = ["baz", "qux"]))]
)
class JpaHelperAnnotationSample(
        @Unique
        var foo: String,
        @Unique
        var bar: String,
        var baz: String,
        var qux: String
) : AbstractEntity()
