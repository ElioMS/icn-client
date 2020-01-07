package com.peruapps.icnclient.features.substances.data

import com.peruapps.icnclient.model.Substance
import com.peruapps.icnclient.network.ApiService
import com.peruapps.icnclient.room.dao.SubstanceDao
import com.peruapps.icnclient.room.entity.SubstanceTable

class SubstanceRepositoryImpl(private val apiService: ApiService,
                              private val substanceDao: SubstanceDao): SubstanceRepository {

    // WEB SERVICES
    override suspend fun listSubstances(): List<Substance> {
        return apiService.substances()
    }

    // ROOM

    override suspend fun getSubstancesByParentId(id: Int): List<SubstanceTable> {
        return substanceDao.findByDetail(id)
    }

    override suspend fun storeSubstances(data: SubstanceTable) {
        substanceDao.insert(data)
    }

}