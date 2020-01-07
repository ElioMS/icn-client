package com.peruapps.icnclient.room.repository

import com.peruapps.icnclient.room.entity.PersonalTable

interface PersonalTableRepository {
    suspend fun list(id: Int): List<PersonalTable>
    suspend fun insert(data: PersonalTable)
}