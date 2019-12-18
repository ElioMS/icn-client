package com.peruapps.icnclient.room.repository

import com.peruapps.icnclient.room.entity.ServiceDetail

interface ServiceDetailRepository {
    suspend fun list(): List<ServiceDetail>
    suspend fun summaryPrice(): Float
    suspend fun insert(data: ServiceDetail)
    suspend fun deleteAll()
}