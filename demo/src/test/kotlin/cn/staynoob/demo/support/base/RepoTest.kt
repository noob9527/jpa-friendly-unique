package cn.staynoob.demo.support.base

import cn.staynoob.springdatajpahelper.autoconfigure.JpaHelperAutoConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManager

@DataJpaTest
@ImportAutoConfiguration(JpaHelperAutoConfiguration::class)
abstract class RepoTest : TestBase() {
    @Autowired
    protected lateinit var entityManager: EntityManager
}
