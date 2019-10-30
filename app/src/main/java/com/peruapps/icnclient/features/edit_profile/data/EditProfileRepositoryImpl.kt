package com.peruapps.icnclient.features.edit_profile.data

import com.peruapps.icnclient.network.ApiService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File

class EditProfileRepositoryImpl(private val apiService: ApiService) : EditProfileRepository {

    override suspend fun updateProfile(
        photo: File?,
        phoneNumber: String,
        email: String,
        gender: String,
        age: Int,
        documentType: Int,
        documentNumber: String,
        address: String,
        addressReference: String
    ): Response<Unit> {

        var body: MultipartBody.Part? = null

        photo?.let {
            val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), photo)
            body = MultipartBody.Part.createFormData("photo", "image.jpg", requestFile)
        }

        val fPhoneNumber = RequestBody.create(MediaType.parse("text/plain"), phoneNumber)
        val fEmail = RequestBody.create(MediaType.parse("text/plain"), email)
        val fGender = RequestBody.create(MediaType.parse("text/plain"), gender)
        val fAge = RequestBody.create(MediaType.parse("text/plain"), age.toString())
        val rbDocumentType = RequestBody.create(MediaType.parse("text/plain"), documentType.toString())
        val rbDocumentNumber = RequestBody.create(MediaType.parse("text/plain"), documentNumber)
        val rbAddress = RequestBody.create(MediaType.parse("text/plain"), address)
        val rbAddressReference = RequestBody.create(MediaType.parse("text/plain"), addressReference)

        return apiService.updateProfile(body, fPhoneNumber, fEmail, fGender, fAge, rbDocumentType, rbDocumentNumber, rbAddress, rbAddressReference)
    }

}