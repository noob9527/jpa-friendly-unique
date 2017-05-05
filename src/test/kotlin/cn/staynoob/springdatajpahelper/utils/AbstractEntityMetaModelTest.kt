package cn.staynoob.springdatajpahelper.utils

import cn.staynoob.springdatajpahelper.fixtures.entity.ColumnSample
import cn.staynoob.springdatajpahelper.fixtures.entity.TableSample
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AbstractEntityMetaModelTest {

    class SampleMetaModel(entity: Any) : AbstractEntityMetaModel(entity) {
        override fun extractPropertyValue(fieldName: String) = TODO()
        override fun extractIdentifier(): PropertyEntry = TODO()
    }

}