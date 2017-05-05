package cn.staynoob.demo.base.entity

import org.springframework.data.domain.Persistable
import org.springframework.util.ClassUtils
import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class AbstractEntity : Persistable<Int> {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int? = null

    override fun getId(): Int? {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    override fun isNew() = this.id == null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    var createAt: Date? = null
        private set

    @Temporal(TemporalType.TIMESTAMP)
    var updateAt: Date? = null
        private set

    @PrePersist
    protected fun onInsert() {
        this.createAt = Date()
    }

    @PreUpdate
    protected fun onUpdate() {
        this.updateAt = Date()
    }

    override fun toString(): String {
        return String.format("EntityType: [%s] with id: [%s]", this::class.simpleName, id)
    }

    override fun equals(other: Any?): Boolean {
        if (null == other) return false
        if (this === other) return true
        if (javaClass != ClassUtils.getUserClass(other)) return false
        val that = other as Persistable<*>
        return this.id != null && this.id == that.id
    }

    override fun hashCode(): Int {
        var hashCode = 17
        val id = this.id
        hashCode += if (null == id) 0 else id.hashCode() * 31
        return hashCode
    }
}
