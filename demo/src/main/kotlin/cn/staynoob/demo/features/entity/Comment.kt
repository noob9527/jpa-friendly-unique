package cn.staynoob.demo.features.entity

import cn.staynoob.demo.base.entity.AbstractEntity
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Comment(
        val content: String,
        @ManyToOne
        @JoinColumn
        val post: Post
) : AbstractEntity()
