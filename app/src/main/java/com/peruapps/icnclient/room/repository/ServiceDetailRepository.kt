package com.peruapps.icnclient.room.repository

import com.peruapps.icnclient.room.entity.ServiceDetail

interface ServiceDetailRepository {
    suspend fun list(): List<ServiceDetail>
    suspend fun countItems(): Int
    suspend fun summaryPrice(): Float
    suspend fun insert(data: ServiceDetail): Long
    suspend fun findById(id: Int) : ServiceDetail
    suspend fun deleteAll()
    suspend fun deleteById(id: Int)
}