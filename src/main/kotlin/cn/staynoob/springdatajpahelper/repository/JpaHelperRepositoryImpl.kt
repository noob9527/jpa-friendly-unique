package cn.staynoob.springdatajpahelper.repository

import cn.staynoob.springdatajpahelper.service.BatchService
import cn.staynoob.springdatajpahelper.service.FriendlyUQService
import cn.staynoob.springdatajpahelper.utils.copy
import cn.staynoob.springdatajpahelper.utils.merge
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable
import javax.persistence.EntityManager

@Suppress("LeakingThis")
class JpaHelperRepositoryImpl<T : Any, ID : Serializable>(
        private val entityInformation: JpaEntityInformation<T, *>,
        private val entityManager: EntityManager
) : SimpleJpaRepository<T, ID>(entityInformation, entityManager),
        JpaHelperRepository<T, ID> {

    private val friendlyUQService = FriendlyUQService<T>(entityManager)
    private val batchService = BatchService(entityManager)

    override fun refresh(entity: T) = entityManager.refresh(entity)

    override fun getById(id: ID): T = findOne(id) ?: throw EmptyResultDataAccessException(1)

    override fun getOne(spec: Specification<T>): T = findOne(spec) ?: throw EmptyResultDataAccessException(1)

    override fun <S : T> getByUQ(entity: S): S = findByUQ(entity) ?: throw EmptyResultDataAccessException(1)

    override fun <S : T> exists(entity: S): Boolean = findByUQ(entity) != null

    /**
     * if id exist, use findOne(id:ID)
     * else find by unique Constraint which marked as identity
     * @see
     */
    @Suppress("UNCHECKED_CAST")
    override fun <S : T> findByUQ(entity: S): S? {
        val id = entityInformation.getId(entity) as ID?
        return if (id != null) findOne(id) as S
        else friendlyUQService.findByUQ(entity)
    }

    @Transactional
    override fun <S : T> save(entity: S): S {
        friendlyUQService.validate(entity)
        return super.save(entity)
    }

    @Transactional
    override fun <S : T> saveAndFlush(entity: S): S
            = save(entity).also { flush() }

    @Transactional
    override fun <S : T> create(entity: S): S = save(entity)

    @Transactional
    override fun <S : T> update(entity: S): S {
        val target = getByUQ(entity)
        val ignoreProperties = entityInformation.idAttributeNames
                .toSet().toTypedArray()
        copy(entity, target, *ignoreProperties)
        return save(target)
    }

    @Transactional
    override fun <S : T> patch(entity: S): S {
        val target = getByUQ(entity)
        merge(entity, target)
        return save(target)
    }

    @Transactional
    override fun <S : T> createOrUpdate(entity: S): S
            = if (exists(entity)) update(entity) else create(entity)

    @Transactional
    override fun <S : T> createOrPatch(entity: S): S
            = if (exists(entity)) patch(entity) else create(entity)

    @Transactional
    override fun <S : T> create(entities: Iterable<S>, isAtomic: Boolean): Iterable<S>
            = batchService.batch(entities, this::create, isAtomic)

    @Transactional
    override fun <S : T> update(entities: Iterable<S>, isAtomic: Boolean): Iterable<S>
            = batchService.batch(entities, this::update, isAtomic)

    @Transactional
    override fun <S : T> patch(entities: Iterable<S>, isAtomic: Boolean): Iterable<S>
            = batchService.batch(entities, this::patch, isAtomic)

    @Transactional
    override fun <S : T> createOrUpdate(entities: Iterable<S>, isAtomic: Boolean): Iterable<S>
            = batchService.batch(entities, this::createOrUpdate, isAtomic)

    @Transactional
    override fun <S : T> createOrPatch(entities: Iterable<S>, isAtomic: Boolean): Iterable<S>
            = batchService.batch(entities, this::createOrPatch, isAtomic)
}