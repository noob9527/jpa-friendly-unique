package cn.staynoob.springdatajpahelper.utils

import cn.staynoob.springdatajpahelper.fixtures.entity.ColumnSample
import cn.staynoob.springdatajpahelper.support.base.TestBase
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.persistence.EntityManager

@DataJpaTest
class JpaMetaModelTest:TestBase() {

    @Autowired
    private lateinit var entityManager: EntityManager
    private lateinit var metaModel: AbstractEntityMetaModel
    private lateinit var columnSample: ColumnSample

    @BeforeEach
    fun setUp() {
        columnSample = ColumnSample("fooValue", "barValue", "bazValue")
                .apply { id = Int.MAX_VALUE }
        metaModel = JpaMetaModel(columnSample, entityManager)
    }

    @Test
    @DisplayName("ExtractPropertyValue提取属性值")
    fun extractPropertyValue100() {
        val propList = listOf("foo", "bar", "baz")
        val result = propList
                .map { metaModel.extractPropertyValue(it) }
        assertThat(result)
                .hasSameElementsAs(listOf(
                        columnSample.foo,
                        columnSample.bar,
                        columnSample.baz
                ))
    }

    @Test
    @DisplayName("extractPropertyValue无法提取的属性值抛出异常")
    fun extractPropertyValue200() {
        assertThatThrownBy { metaModel.extractPropertyValue("whatever") }
                .isInstanceOf(NullPointerException::class.java)
    }

    @Test
    @DisplayName("ExtractIdentifier")
    fun extractIdentifier() {
        val (key, value) = metaModel.extractIdentifier()
        assertThat(key).isEqualTo("id")
        assertThat(value).isEqualTo(Int.MAX_VALUE)
    }
}