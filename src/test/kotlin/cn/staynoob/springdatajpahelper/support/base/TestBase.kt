package cn.staynoob.springdatajpahelper.support.base

import cn.staynoob.springdatajpahelper.autoconfigure.JpaHelperAutoConfiguration
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ImportAutoConfiguration(JpaHelperAutoConfiguration::class)
abstract class TestBase
