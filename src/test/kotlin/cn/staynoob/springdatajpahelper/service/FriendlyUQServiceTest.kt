package cn.staynoob.springdatajpahelper.service

import cn.staynoob.springdatajpahelper.exception.DuplicatedEntityException
import cn.staynoob.springdatajpahelper.fixtures.entity.UniqueSample
import cn.staynoob.springdatajpahelper.support.base.RepoTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class FriendlyUQServiceTest : RepoTest() {

    private lateinit var friendlyUQService: FriendlyUQService<UniqueSample>

    @BeforeEach
    fun setUp() {
        friendlyUQService = FriendlyUQService(entityManager)
    }

    @Test
    @DisplayName("basic validate test")
    fun validate100() {
        val sample1 = UniqueSample("1")
        val sample2 = UniqueSample("1")
        entityManager.persist(sample1)
        try {
            friendlyUQService.validate(sample2)
            fail("shouldn't run this line")
        } catch (e: DuplicatedEntityException) {
            assertThat(e.domainClass).isEqualTo(UniqueSample::class)
            assertThat(e.fieldMap["name"]).isEqualTo("1")
        }
    }

    @Test
    @DisplayName("basic findByUQ test")
    fun findByUQTest100() {
        val sample1 = UniqueSample("1")
        entityManager.persist(sample1)
        val res = friendlyUQService.findByUQ(UniqueSample("1"))
        assertThat(res).isNotNull()
        assertThat(res).isEqualTo(sample1)
    }

    @Test
    @DisplayName("basic findByUQ not exist should return null")
    fun findByUQTest200() {
        val res = friendlyUQService.findByUQ(UniqueSample("1"))
        assertThat(res).isNull()
    }
}