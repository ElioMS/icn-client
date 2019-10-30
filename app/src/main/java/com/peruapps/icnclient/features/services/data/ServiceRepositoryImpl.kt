package com.peruapps.icnclient.features.services.data

import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.network.ApiService

class ServiceRepositoryImpl(private val apiService: ApiService): ServiceRepository {

    override suspend fun listItemsByService(serviceId: Int): List<ServiceType> {
        return apiService.items(serviceId)
    }

    override suspend fun listServicesByCategory(categoryId: Int): List<Service> {
        return apiService.services(categoryId)
    }

}