package cn.staynoob.springdatajpahelper.fixtures.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(uniqueConstraints = [
    UniqueConstraint(columnNames = ["foo", "bar"]),
    UniqueConstraint(columnNames = ["foo", "baz_qux"])
])
data class TableSample(
        var foo: String,
        var bar: String,
        @Column(name = "baz_qux")
        var bazQux: String
) : AbstractEntity()
