package cn.staynoob.springdatajpahelper.fixtures.repo

import cn.staynoob.springdatajpahelper.fixtures.entity.AbstractEntity
import cn.staynoob.springdatajpahelper.repository.BatchCrudByIdentityUQRepository
import cn.staynoob.springdatajpahelper.repository.CrudByIdentityUQRepository
import cn.staynoob.springdatajpahelper.repository.FriendlyUQRepository
import cn.staynoob.springdatajpahelper.repository.LegacyNullSafetyRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface BaseRepo<T : AbstractEntity> :
        Repository<T, Int>,
        LegacyNullSafetyRepository<T, Int>,
        FriendlyUQRepository<T>,
        CrudByIdentityUQRepository<T>,
        BatchCrudByIdentityUQRepository<T>
