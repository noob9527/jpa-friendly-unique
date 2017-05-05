package cn.staynoob.demo.features.api

import cn.staynoob.demo.features.entity.Post
import cn.staynoob.demo.features.repo.PostRepo
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/post"], produces = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
class PostCtrl(
        private val postRepo: PostRepo
) {
    @PostMapping
    fun create(post: Post) = postRepo.save(post)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int) = postRepo.getById(id)
}