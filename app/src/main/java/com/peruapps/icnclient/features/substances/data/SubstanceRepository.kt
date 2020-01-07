package com.peruapps.icnclient.features.substances.data

import com.peruapps.icnclient.model.Substance
import com.peruapps.icnclient.room.entity.SubstanceTable

interface SubstanceRepository {
    // WEB SERVICE
    suspend fun listSubstances(): List<Substance>

    // ROOM
    suspend fun getSubstancesByParentId(id: Int): List<SubstanceTable>
    suspend fun storeSubstances(data: SubstanceTable)
}