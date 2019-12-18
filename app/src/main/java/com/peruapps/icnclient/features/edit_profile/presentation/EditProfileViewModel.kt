package com.peruapps.icnclient.features.edit_profile.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.peruapps.icnclient.db.sharePreferences.PreferencesManager
import com.peruapps.icnclient.features.edit_profile.data.EditProfileRepository
import com.peruapps.icnclient.model.response.LoginResponse
import com.peruapps.icnclient.ui.base.BaseViewModel
import java.io.File
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipeline


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
        setGender(data.gender)
        setDocumentType(data.documentType)
        documentNumber.set(data.document_number)
        address.set(data.address)
        addressReference.set(data.addressReference)
    }

    private fun setGender(value: String) {
        val genderToInt = when (value) {
            "M" -> 0
            else -> 1
        }

        gender.set(genderToInt)
    }

    fun setDocumentType(value: Int?) {
        value?.let { documentType.set(it) }
    }

    fun showChangePasswordView() {
        getNavigator().showChangePasswordView()
    }

    fun saveProfile() {

        val genderString = when (gender.get()) {
            0 -> "M"
            else -> "F"
        }

        profile.get()!!.phone_number = phoneNumber.get()!!
        profile.get()!!.email = email.get()!!
        profile.get()!!.gender = genderString
        profile.get()!!.age = age.get()
        profile.get()!!.documentType = documentType.get()
        profile.get()!!.document_number = documentNumber.get()!!

        address.get()?.let {
            profile.get()!!.address = it
        }

        addressReference.get()?.let {
            profile.get()!!.addressReference = it
        }

        //CLEAR FRESCO CACHE
        val imagePipeline = Fresco.getImagePipeline()
        imagePipeline.clearCaches()

        startJob {
            Log.d("AAAA", "aaaaa")
            repository.updateProfile(
                loadedPicture.get(),
                phoneNumber.get()!!, email.get()!!, genderString, age.get(),
                documentType.get(), documentNumber.get()!!,
                address.get()!!, addressReference.get()!!
            )
            Log.d("AAAA", "bbbb")

            preferencesManager.saveAuthData(profile.get()!!)
            getNavigator().updateUserData()
        }
    }

    fun onClickAddPhoto() {
        getNavigator().showGalleryDialog()
    }

    fun onClickCloseView() {
        getNavigator().goBack()
    }
}