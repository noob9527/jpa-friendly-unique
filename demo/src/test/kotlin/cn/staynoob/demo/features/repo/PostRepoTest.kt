package cn.staynoob.demo.features.repo

import cn.staynoob.demo.features.entity.Post
import cn.staynoob.demo.support.base.RepoTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException

class PostRepoTest : RepoTest() {

    @Autowired
    private lateinit var postRepo: PostRepo

    private val post1 = Post("foo")
    private val post2 = Post("bar")

    @BeforeEach
    fun setUp() {
        postRepo.save(listOf(post1, post2))
        postRepo.flush()
    }

    @Test
    @DisplayName("getById")
    fun getByIdTest100() {
        assertThat(postRepo.getById(post1.id!!))
                .isEqualTo(post1)
    }

    @Test
    @DisplayName("查找不存在的对象应该抛出对应的异常")
    fun getByIdTest200() {
        assertThatThrownBy { postRepo.getById(Int.MAX_VALUE) }
                .isInstanceOf(EmptyResultDataAccessException::class.java)
    }
}