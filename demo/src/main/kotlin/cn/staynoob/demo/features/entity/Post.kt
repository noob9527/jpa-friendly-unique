package cn.staynoob.demo.features.entity

import cn.staynoob.demo.base.entity.AbstractEntity
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.OneToMany

@Entity
data class Post(
        val title: String
) : AbstractEntity() {
    @OneToMany(mappedBy = "post")
    val comments: MutableList<Comment> = mutableListOf()

    @ManyToMany
    val tags: MutableSet<Tag> = mutableSetOf()
}
