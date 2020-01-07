package com.peruapps.icnclient.room.repository

import com.peruapps.icnclient.room.dao.PersonalDao
import com.peruapps.icnclient.room.entity.PersonalTable

class PersonalTableRepositoryImpl (private val personalDao: PersonalDao): PersonalTableRepository {

    override suspend fun list(id: Int): List<PersonalTable> {
        return personalDao.list(id)
    }

    override suspend fun insert(data: PersonalTable) {
        personalDao.insert(data)
    }
}