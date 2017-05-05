package cn.staynoob.springdatajpahelper.repository

interface FriendlyUQRepository<in T> {
    fun <S : T> findByUQ(entity: S): S?
    fun <S : T> getByUQ(entity: S): S
    fun <S : T> exists(entity: S): Boolean
    fun <S : T> save(entity: S): S
    fun <S : T> saveAndFlush(entity: S): S
}