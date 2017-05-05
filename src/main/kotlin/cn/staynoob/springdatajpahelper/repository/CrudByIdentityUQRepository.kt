package cn.staynoob.springdatajpahelper.repository

interface CrudByIdentityUQRepository<in T> : FriendlyUQRepository<T> {
    fun <S : T> create(entity: S): S
    fun <S : T> update(entity: S): S
    fun <S : T> patch(entity: S): S
    fun <S : T> createOrUpdate(entity: S): S
    fun <S : T> createOrPatch(entity: S): S
}