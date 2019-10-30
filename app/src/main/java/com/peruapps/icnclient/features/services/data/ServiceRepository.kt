package com.peruapps.icnclient.features.services.data

import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType

interface ServiceRepository {
    suspend fun listServicesByCategory(categoryId: Int): List<Service>
    suspend fun listItemsByService(serviceId: Int): List<ServiceType>
}