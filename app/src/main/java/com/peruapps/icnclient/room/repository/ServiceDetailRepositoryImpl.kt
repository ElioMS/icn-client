package com.peruapps.icnclient.room.repository

import com.peruapps.icnclient.room.dao.ServiceDetailDao
import com.peruapps.icnclient.room.entity.ServiceDetail

class ServiceDetailRepositoryImpl (private val serviceDetailDao: ServiceDetailDao): ServiceDetailRepository {

    override suspend fun countItems(): Int {
        return serviceDetailDao.countItems()
    }

    override suspend fun deleteById(id: Int) {
        serviceDetailDao.deleteById(id)
    }

    override suspend fun findById(id: Int): ServiceDetail {
        return serviceDetailDao.findById(id)
    }

    override suspend fun deleteAll() {
        serviceDetailDao.deleteAll()
    }

    override suspend fun summaryPrice(): Float {
        return serviceDetailDao.sumPrice()
    }

    override suspend fun list(): List<ServiceDetail> {
        return serviceDetailDao.list()
    }

    override suspend fun insert(data: ServiceDetail): Long {
        return serviceDetailDao.insert(data)
    }
}