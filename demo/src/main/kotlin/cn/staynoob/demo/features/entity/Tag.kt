package cn.staynoob.demo.features.entity

import cn.staynoob.demo.base.entity.AbstractEntity
import javax.persistence.Entity

@Entity
data class Tag(
        val name: String
) : AbstractEntity()
