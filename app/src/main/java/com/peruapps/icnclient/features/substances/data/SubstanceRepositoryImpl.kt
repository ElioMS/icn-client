package com.peruapps.icnclient.features.substances.data

import com.peruapps.icnclient.model.Substance
import com.peruapps.icnclient.network.ApiService

class SubstanceRepositoryImpl(private val apiService: ApiService): SubstanceRepository {

    override suspend fun listSubstances(): List<Substance> {
        return apiService.substances()
    }

}