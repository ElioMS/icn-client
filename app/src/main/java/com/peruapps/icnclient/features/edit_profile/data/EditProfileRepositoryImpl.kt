package com.peruapps.icnclient.features.edit_profile.data

import com.peruapps.icnclient.model.request.UpdateProfileRequest
import com.peruapps.icnclient.network.ApiService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

class EditProfileRepositoryImpl(private val apiService: ApiService) : EditProfileRepository {

    override suspend fun updateProfile(
        photo: File,
        phoneNumber: String,
        email: String,
        gender: String,
        age: Int
    ): Response<Unit> {

        val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), photo)
        val body = MultipartBody.Part.createFormData("photo", "image.jpg", requestFile)

        val fPhoneNumber = RequestBody.create(MediaType.parse("text/plain"), phoneNumber)
        val fEmail = RequestBody.create(MediaType.parse("text/plain"), email)
        val fGender = RequestBody.create(MediaType.parse("text/plain"), gender)
        val fAge = RequestBody.create(MediaType.parse("text/plain"), age.toString())

        return apiService.updateProfile(body, fPhoneNumber, fEmail, fGender, fAge)
    }

}