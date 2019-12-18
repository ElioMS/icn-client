package com.peruapps.icnclient.room.repository

import com.peruapps.icnclient.room.dao.ServiceDetailDao
import com.peruapps.icnclient.room.entity.ServiceDetail

class ServiceDetailRepositoryImpl (private val serviceDetailDao: ServiceDetailDao): ServiceDetailRepository {

    override suspend fun deleteAll() {
        serviceDetailDao.deleteAll()
    }

    override suspend fun summaryPrice(): Float {
        return serviceDetailDao.sumPrice()
    }

    override suspend fun list(): List<ServiceDetail> {
        return serviceDetailDao.list()
    }

    override suspend fun insert(data: ServiceDetail) {
        serviceDetailDao.insert(data)
    }
}