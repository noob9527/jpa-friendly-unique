package cn.staynoob.springdatajpahelper.fixtures.entity

import cn.staynoob.springdatajpahelper.annotation.Unique
import javax.persistence.Entity

@Entity
data class UniqueSample(
        @Unique
        var name: String
) : AbstractEntity()
