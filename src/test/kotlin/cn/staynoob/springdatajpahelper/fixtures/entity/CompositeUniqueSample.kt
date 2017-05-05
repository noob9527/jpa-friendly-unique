package cn.staynoob.springdatajpahelper.fixtures.entity

import cn.staynoob.springdatajpahelper.annotation.CompositeUnique
import cn.staynoob.springdatajpahelper.annotation.FriendlyUnique
import javax.persistence.Entity

@Entity
@FriendlyUnique(
        compositeUniques = [(CompositeUnique(fields = ["foo", "bar"]))]
)
data class CompositeUniqueSample(
        val foo: String,
        val bar: String
) : AbstractEntity()