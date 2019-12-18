package com.peruapps.icnclient.features.register.data

import android.util.Log
import com.peruapps.icnclient.db.sharePreferences.PreferencesManager
import com.peruapps.icnclient.model.request.RegisterRequest
import com.peruapps.icnclient.model.response.LoginResponse
import com.peruapps.icnclient.network.ApiService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RegisterRepositoryImpl(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager
) : RegisterRepository {

    override suspend fun createClientAccount(
        photo: File?,
        name: String,
        surname: String,
        gender: String,
        documentType: Int,
        documentNumber: String,
        age: Int,
        email: String,
        phone: String,
        address: String,
        addressReference: String?,
        password: String
    ): LoginResponse {

        var body: MultipartBody.Part? = null

        photo?.let {
            val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), photo)
            body = MultipartBody.Part.createFormData("photo", "image.jpg", requestFile)
        }

        val rbName = RequestBody.create(MediaType.parse("text/plain"), name)
        val rbSurname = RequestBody.create(MediaType.parse("text/plain"), surname)
        val rbGender = RequestBody.create(MediaType.parse("text/plain"), gender)

        val rbDocumentType = RequestBody.create(MediaType.parse("text/plain"), documentType.toString())
        val rbDocumentNumber = RequestBody.create(MediaType.parse("text/plain"), documentNumber)
        val rbAge = RequestBody.create(MediaType.parse("text/plain"), age.toString())
        val rbEmail = RequestBody.create(MediaType.parse("text/plain"), email)
        val rbPhone = RequestBody.create(MediaType.parse("text/plain"), phone)
        val rbAddress = RequestBody.create(MediaType.parse("text/plain"), address)

        val rbAddressReference = addressReference?.let {
            RequestBody.create(MediaType.parse("text/plain"), it)
        }

        val isAdmin = RequestBody.create(MediaType.parse("text/plan"), "0")
        val type = RequestBody.create(MediaType.parse("text/plan"), "1")
        val rbPassword = RequestBody.create(MediaType.parse("text/plan"), password)

        val response = apiService.createAccount(
            body,
            rbName,
            rbSurname,
            rbGender,
            rbDocumentType,
            rbDocumentNumber,
            rbAge,
            rbEmail,
            rbPhone,
            rbAddress,
            rbAddressReference,
            isAdmin,
            type,
            rbPassword
        )

        Log.d("REGISTER_RESPONSE", response.toString())

        preferencesManager.saveAuthData(response)
        return response
    }

}