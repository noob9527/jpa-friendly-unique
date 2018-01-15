package cn.staynoob.springdatajpahelper.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class BeanUtilsKtTest {
    data class Foo(
            var name: String? = null,
            var age: Int = 0,
            var bar: Bar? = null
    ) {
        val readOnly: String
            get() = throw Exception("shouldn't be invoke!")
    }

    data class Bar(
            var name: String? = null
    )

    @Test
    @DisplayName("shallow copy")
    fun copyTest100() {
        val bar = Bar("bar")
        val src = Foo("foo", 1, bar)
        val target = Foo()
        copy(src, target)
        assertThat(target.name).isEqualTo("foo")
        assertThat(target.age).isEqualTo(1)
        assertThat(target.bar === bar).isTrue()
    }

    @Test
    @DisplayName("copy ignore properties")
    fun copyTest200() {
        val src = Foo("foo", 1)
        val target = Foo("target")
        copy(src, target, Foo::name)
        assertThat(target.name).isEqualTo("target")
        assertThat(target.age).isEqualTo(1)
    }

    @Test
    @DisplayName("should ignore readonly method")
    fun mergeTest50() {
        assertThat(getIgnoreProperties(Foo()))
                .contains(Foo::readOnly.name)
    }

    @Test
    @DisplayName("merge should ignore null properties")
    fun mergeTest100() {
        val src = Foo(null, 1)
        val target = Foo("target")
        merge(src, target)
        assertThat(target.name).isEqualTo("target")
        assertThat(target.age).isEqualTo(1)
    }

    @Test
    @DisplayName("merge ignore properties")
    fun mergeTest200() {
        val src = Foo("src", 1)
        val target = Foo("target", 0, Bar())
        merge(src, target, Foo::name)
        assertThat(target.name).isEqualTo("target")
        assertThat(target.age).isEqualTo(1)
        assertThat(target.bar).isNotNull()
    }

}