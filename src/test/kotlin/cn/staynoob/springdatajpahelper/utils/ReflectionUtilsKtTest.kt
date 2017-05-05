package cn.staynoob.springdatajpahelper.utils

import cn.staynoob.springdatajpahelper.annotation.CompositeUnique
import cn.staynoob.springdatajpahelper.annotation.FriendlyUnique
import cn.staynoob.springdatajpahelper.annotation.Unique
import cn.staynoob.springdatajpahelper.autoconfigure.JpaHelperProperties
import cn.staynoob.springdatajpahelper.fixtures.entity.ColumnSample
import cn.staynoob.springdatajpahelper.fixtures.entity.TableSample
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ReflectionUtilsKtTest {
    data class Sample(val foo: String, val bar: String? = null)

    @Test
    @DisplayName("HasField")
    fun hasFieldTest() {
        assertThat(hasField(Sample::class, "foo")).isTrue()
        assertThat(hasField(Sample::class, "bar")).isTrue()
        assertThat(hasField(Sample::class, "baz")).isFalse()
    }


    @Test
    @DisplayName("GetPropertyValue")
    fun getPropertyValueTest() {
        val sample = Sample("fooValue")
        assertThat(getPropertyValue(sample, "foo"))
                .isEqualTo(sample.foo)
        assertThat(getPropertyValue(sample, "bar"))
                .isNull()
        assertThatThrownBy { getPropertyValue(sample, "baz") }
    }

    @Nested
    @DisplayName("ExtractUQByJpaAnnotation")
    inner class ExtractUQByJpaAnnotation {
        @Test
        @DisplayName("column sample")
        fun columnSample() {
            assertThat(extractUQByJpaAnnotation(ColumnSample::class))
                    .hasSameElementsAs(setOf(setOf("foo"), setOf("bar")))
        }

        @Test
        @DisplayName("table Sample")
        fun tableSample() {
            assertThat(extractUQByJpaAnnotation(TableSample::class))
                    .hasSameElementsAs(setOf(
                            setOf("foo", "bar"),
                            setOf("foo", "bazQux")
                    ))
        }
    }


    @FriendlyUnique([
        CompositeUnique(["foo"], isConstraint = true, isIdentity = true),
        CompositeUnique(["bar"], isConstraint = false, isIdentity = false)
    ])
    data class AnnotationSample1(
            val foo: String? = null,
            val bar: String? = null,
            @Unique(isConstraint = true, isIdentity = true)
            val baz: String? = null,
            @Unique(isConstraint = false, isIdentity = false)
            val qux: String? = null,

            val noise: String? = null
    )

    @FriendlyUnique([
        CompositeUnique(["bar"], queryPriority = 2),
        CompositeUnique(["foo"], queryPriority = 1)
    ])
    data class QueryPrioritySample(
            val foo: String? = null,
            val bar: String? = null,
            @Unique(queryPriority = 4)
            val qux: String? = null,
            @Unique(queryPriority = 3)
            val baz: String? = null
    )

    @Nested
    @DisplayName("ExtractUniqueConstraint")
    inner class ExtractUniqueConstraint {

        @BeforeEach
        fun setUp() {
            JpaHelperPropertiesContainer
                    .createInstance(JpaHelperProperties())
        }

        @Test
        @DisplayName("basic case")
        fun extractUniqueConstraintTest100() {
            val res = extractUniqueConstraint(AnnotationSample1::class)
                    .flatMap { it.fields }
                    .toSet()
            assertThat(res)
                    .contains("foo", "bar", "baz", "qux")
                    .doesNotContain("noise")
        }

        @Test
        @DisplayName("should filter by isIdentity")
        fun extractIdentityUQTest100() {
            val res = extractIdentityUQ(AnnotationSample1::class)
                    .flatMap { it.asIterable() }
                    .toSet()
            assertThat(res)
                    .contains("foo", "baz")
                    .doesNotContain("bar", "qux")
        }

        @Test
        @DisplayName("should sort by queryPriority")
        fun extractIdentityUQTest200() {
            val res = extractIdentityUQ(QueryPrioritySample::class)
                    .flatMap { it.asIterable() }
            assertThat(res)
                    .containsExactly("foo", "bar", "baz", "qux")
        }

        @Test
        @DisplayName("should filter by isConstraint")
        fun extractValidationUQTest100() {
            val res = extractValidationUQ(AnnotationSample1::class)
                    .flatMap { it.asIterable() }
                    .toSet()
            assertThat(res)
                    .contains("foo", "baz")
                    .doesNotContain("bar", "qux")
        }
    }
}