package com.peruapps.icnclient.features.edit_profile.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.peruapps.icnclient.db.sharePreferences.PreferencesManager
import com.peruapps.icnclient.features.edit_profile.data.EditProfileRepository
import com.peruapps.icnclient.model.request.UpdateProfileRequest
import com.peruapps.icnclient.model.response.LoginResponse
import com.peruapps.icnclient.ui.base.BaseViewModel
import java.io.File
import android.provider.MediaStore



class EditProfileViewModel(
    private val repository: EditProfileRepository,
    private val preferencesManager: PreferencesManager
) : BaseViewModel<EditProfileNavigator>() {

    var profile = ObservableField<LoginResponse>()

    val phoneNumber = ObservableField<String>("")
    val email = ObservableField<String>("")
    val age = ObservableInt(0)
    val documentType = ObservableInt(0)
    val documentNumber = ObservableField<String>("")
    val address = ObservableField<String>("")
    val addressReference = ObservableField<String>("")
    val gender = ObservableInt(0)
    val photo = ObservableField<String>()
    val loadedPicture = ObservableField<File>()

    init {
        getUserDataFromPreferences()
    }

    private fun getUserDataFromPreferences() {
        profile.set(preferencesManager.getUserData())
        setData()
    }

    private fun setData() {
        val data = profile.get()!!

        phoneNumber.set(data.phone_number)
        email.set(data.email)
        age.set(data.age)
        setDocumentType(data.documentType)
        documentNumber.set(data.document_number)
        address.set(data.address)
        addressReference.set(data.addressReference)
    }

    fun setDocumentType(value: Int) {
        documentType.set(value)
    }

    fun showChangePasswordView() {
        getNavigator().showChangePasswordView()
    }

    fun saveProfile() {

        val genderString = when (gender.get()) {
            0 -> "M"
            else -> "F"
        }

        profile.get()!!.email = email.get()!!
        profile.get()!!.gender = genderString
        profile.get()!!.documentType = documentType.get()
        profile.get()!!.document_number = documentNumber.get()!!
        profile.get()!!.phone_number = phoneNumber.get()!!
        profile.get()!!.address = address.get()!!
        profile.get()!!.addressReference = addressReference.get()!!
        profile.get()!!.age = age.get()

//        val data = UpdateProfileRequest(
//            email.get()!!,
//            genderString,
//            documentType.get(),
//            documentNumber.get()!!,
//            phoneNumber.get()!!,
//            address.get()!!,
//            addressReference.get()!!,
//            age.get()
//        )

        Log.d("save_profile", loadedPicture.get()!!.toString())

        startJob {
            val response = repository.updateProfile(loadedPicture.get()!!,
                phoneNumber.get()!!, email.get()!!, genderString, age.get())
//            val response = repository.updateProfile(data)
            preferencesManager.saveAuthData(profile.get()!!)
            getNavigator().updateUserData()
        }
    }

    fun onClickAddPhoto() {
        getNavigator().showGalleryDialog()
    }
}