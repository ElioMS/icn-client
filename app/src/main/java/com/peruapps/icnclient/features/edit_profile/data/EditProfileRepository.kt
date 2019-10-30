package com.peruapps.icnclient.features.edit_profile.data

import retrofit2.Response
import java.io.File

interface EditProfileRepository {
    suspend fun updateProfile(photo: File? = null,
                              phoneNumber: String,
                              email: String,
                              gender: String,
                              age: Int,
                              documentType: Int,
                              documentNumber: String,
                              address: String,
                              addressReference: String): Response<Unit>
}