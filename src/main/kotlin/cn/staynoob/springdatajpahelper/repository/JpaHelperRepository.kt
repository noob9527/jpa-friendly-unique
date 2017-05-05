package cn.staynoob.springdatajpahelper.repository

import java.io.Serializable

interface JpaHelperRepository<T, in ID : Serializable> :
        RefreshableRepository<T>,
        LegacyNullSafetyRepository<T, ID>,
        FriendlyUQRepository<T>,
        CrudByIdentityUQRepository<T>,
        BatchCrudByIdentityUQRepository<T>
