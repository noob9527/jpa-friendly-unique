package cn.staynoob.springdatajpahelper.fixtures.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity
data class ColumnSample(
        @Column(unique = true)
        var foo: String,
        @Column(unique = true)
        var bar: String? = null,
        var baz: String? = null
) : AbstractEntity()
