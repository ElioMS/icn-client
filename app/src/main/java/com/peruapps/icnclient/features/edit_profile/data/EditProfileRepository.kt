package com.peruapps.icnclient.features.edit_profile.data

import com.peruapps.icnclient.model.request.UpdateProfileRequest
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

interface EditProfileRepository {
    suspend fun updateProfile(photo: File,
                              phoneNumber: String,
                              email: String,
                              gender: String,
                              age: Int): Response<Unit>
}