package cn.staynoob.springdatajpahelper.repository

interface RefreshableRepository<in T> {
    fun refresh(entity: T)
}