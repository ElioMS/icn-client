package com.peruapps.icnclient.features.substances.data

import com.peruapps.icnclient.model.Substance

interface SubstanceRepository {
    suspend fun listSubstances(): List<Substance>
}