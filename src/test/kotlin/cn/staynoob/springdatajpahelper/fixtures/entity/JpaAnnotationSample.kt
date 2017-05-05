package cn.staynoob.springdatajpahelper.fixtures.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(
        uniqueConstraints = [(UniqueConstraint(columnNames = ["baz", "qux"]))]
)
data class JpaAnnotationSample(
        @Column(unique = true)
        var foo: String,
        @Column(unique = true)
        var bar: String,
        var baz: String,
        var qux: String
) : AbstractEntity()
