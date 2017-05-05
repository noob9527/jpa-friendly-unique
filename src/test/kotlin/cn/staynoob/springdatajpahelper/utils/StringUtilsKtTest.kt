package cn.staynoob.springdatajpahelper.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class StringUtilsKtTest {
    @Test
    @DisplayName("ConvertCaseByDefault")
    fun convertCaseByDefaultTest() {
        assertThat(convertCaseByDefault("foo_bar"))
                .isEqualTo("fooBar")
    }
}