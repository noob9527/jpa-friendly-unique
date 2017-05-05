package cn.staynoob.demo.base.repo

import cn.staynoob.demo.base.entity.AbstractEntity
import cn.staynoob.springdatajpahelper.repository.CrudByIdentityUQRepository
import cn.staynoob.springdatajpahelper.repository.FriendlyUQRepository
import cn.staynoob.springdatajpahelper.repository.LegacyNullSafetyRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface BaseRepo<T : AbstractEntity>
    : Repository<T, Int>,
        LegacyNullSafetyRepository<T, Int>,
        FriendlyUQRepository<T>,
        CrudByIdentityUQRepository<T> {

//    fun findById(id: Int): T?
//
//    fun findOne(spec: Specification<T>): T?
//
//    fun findAll(spec: Specification<T>?): MutableList<T>
//
//    fun findAll(spec: Specification<T>?, pageable: Pageable): Page<T>
//
//    fun findAll(spec: Specification<T>?, sort: Sort): MutableList<T>
//
//    fun count(spec: Specification<T>?): Long
//
//    fun exists(id: Int): Boolean
//
    fun <S : T> save(entities: Iterable<S>): Iterable<S>

    fun flush()
//
//    fun delete(id: Int)
//
//    fun delete(entity: T)
//
//    fun delete(entities: Iterable<T>)
}
