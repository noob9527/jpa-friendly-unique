package cn.staynoob.springdatajpahelper.repository

interface BatchCrudByIdentityUQRepository<in T> : FriendlyUQRepository<T> {
    fun <S : T> create(entities: Iterable<S>, isAtomic: Boolean = true): Iterable<S>
    fun <S : T> update(entities: Iterable<S>, isAtomic: Boolean = true): Iterable<S>
    fun <S : T> patch(entities: Iterable<S>, isAtomic: Boolean = true): Iterable<S>
    fun <S : T> createOrUpdate(entities: Iterable<S>, isAtomic: Boolean = true): Iterable<S>
    fun <S : T> createOrPatch(entities: Iterable<S>, isAtomic: Boolean = true): Iterable<S>
}