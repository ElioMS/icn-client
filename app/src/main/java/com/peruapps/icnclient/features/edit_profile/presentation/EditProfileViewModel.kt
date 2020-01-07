package com.peruapps.icnclient.features.edit_profile.presentation

import android.util.Log
import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.db.sharePreferences.PreferencesManager
import com.peruapps.icnclient.features.edit_profile.data.EditProfileRepository
import com.peruapps.icnclient.model.response.LoginResponse
import com.peruapps.icnclient.ui.base.BaseViewModel
import java.io.File
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipeline
import com.peruapps.icnclient.R

class EditProfileViewModel(
    private val repository: EditProfileRepository,
    private val preferencesManager: PreferencesManager
) : BaseViewModel<EditProfileNavigator>() {

    var profile = ObservableField<LoginResponse>()

    val phoneNumber = ObservableField<String>("")
    val email = ObservableField<String>("")
    val age = ObservableField("")
    val documentType = ObservableInt(0)
    val documentNumber = ObservableField<String>("")
    val address = ObservableField<String>("")
    val addressReference = ObservableField<String>("")
    val gender = ObservableInt(0)
    val photo = ObservableField<String>()
    val loadedPicture = ObservableField<File>()

    private val _validationMessage = MutableLiveData<Int>()
    val validationMessage: LiveData<Int>
        get() = _validationMessage

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
        age.set(data.age.toString())
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
        if (validator()) { setProfileData() }
    }

    private fun setProfileData() {
        val genderString = when (gender.get()) {
            0 -> "M"
            else -> "F"
        }

        profile.get()!!.phone_number = phoneNumber.get()!!
        profile.get()!!.email = email.get()!!
        profile.get()!!.gender = genderString
        profile.get()!!.age = age.get()!!.toInt()
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
            repository.updateProfile(
                loadedPicture.get(),
                phoneNumber.get()!!,
                email.get()!!,
                genderString,
                age.get()!!.toInt(),
                documentType.get(),
                documentNumber.get()!!,
                address.get()!!,
                addressReference.get()
            )

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

    private fun validator (): Boolean {
        val phone = phoneNumber.get()
        val email = email.get()
        val age = age.get()
        val address = address.get()
        val documentType = documentType.get()
        val documentNumber = documentNumber.get()

        if (phone == "" || email == "" || age == "" || address == "" || documentNumber == "") {
            _validationMessage.value = R.string.validation_empty
            return false
        }

        if  (phone!!.length != 9) {
            _validationMessage.value = R.string.validation_phone_number
            return false
        }

        if (email != "" && !Patterns.EMAIL_ADDRESS.matcher(email!!).matches()) {
            _validationMessage.value = R.string.validation_email
            return false
        }

        documentNumber?.let {
            if  (documentNumber.length != 8 && documentType == 1) {
                _validationMessage.value = R.string.validation_dni
                return false
            }

            if  ((documentNumber.length >= 15 && documentType == 2) || (documentNumber.length <= 1 && documentType == 2)) {
                _validationMessage.value = R.string.validation_pasaporte
                return false
            }
        }

        return true
    }
}