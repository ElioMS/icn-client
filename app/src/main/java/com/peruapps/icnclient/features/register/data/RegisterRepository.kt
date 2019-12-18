package com.peruapps.icnclient.features.register.data

import com.peruapps.icnclient.model.response.LoginResponse
import java.io.File

interface RegisterRepository {
    suspend fun createClientAccount(photo: File? = null,
                                    name: String,
                                    surname: String,
                                    gender: String,
                                    documentType: Int,
                                    documentNumber: String,
                                    age: Int,
                                    email: String,
                                    phone: String,
                                    address: String,
                                    addressReference: String? = null,
                                    password: String): LoginResponse
}