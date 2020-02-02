package com.peruapps.icnclient.features.register.presentation

import android.util.Log
import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.register.data.RegisterRepository
import com.peruapps.icnclient.model.request.RegisterRequest

import com.peruapps.icnclient.ui.base.BaseViewModel
import java.io.File

class RegisterViewModel(private val repository: RegisterRepository) : BaseViewModel<RegisterNavigator>() {

    val TAG = RegisterViewModel::class.java.simpleName

    var name = ObservableField("")
    var surname = ObservableField("")
    var gender = ObservableField("")
    var documentType = ObservableField(1)
    var documentNumber = ObservableField("")
    var age = ObservableField("")
    var email = ObservableField("")
    var phone = ObservableField("")
    var address = ObservableField("")
    var addressReference = ObservableField("")
    var password = ObservableField("")
    var confirmPassword = ObservableField("")
    val loadedPicture = ObservableField<File>()

    private val _showMessage = MutableLiveData("")
    val showMessage: LiveData<String>
        get() = _showMessage

    fun onClickNextButton() {
        val validator = infoValidator()
//
        if (!validator.status) {
//            _showMessage.value = ""
            getNavigator().showPasswordView()
       } else {
            _showMessage.value = validator.message?.let { it } ?: run { "Por favor complete los campos vacíos" }
        }
    }

    fun setGender(value: Int) {
        when (value) {
            1 -> gender.set("M")
            else -> gender.set("F")
        }
    }

    fun setDocumentType(value: Int) = documentType.set(value)

    private fun infoValidator(): ValidatorState {
        val name = name.get()
        val surname = surname.get()
        val gender = gender.get()
        val documentType = documentType.get()
        val documentNumber = documentNumber.get()
        val age = age.get()
        val email = email.get()
        val phone = phone.get()
        val address = address.get()

        val validation = ValidatorState(true)

        if (name == "" || surname == "" || gender == "" || documentNumber == "") { return validation }

        documentNumber?.let {
            if  (documentNumber.length != 8 && documentType == 1) {
                return validation.apply { message = "Formato de DNI incorrecto" }
            }

            if  ( (documentNumber.length >= 15 && documentType == 2) || (documentNumber.length <= 1 && documentType == 2)) {
                return validation.apply { message = "Formato de pasaporte incorrecto" }
            }
        }

        if (age == "") { return validation }

        if (age !== "" && age?.toInt()!! < 18) {
            return validation.apply { message = "Es necesario que sea mayor de edad" }
        }

        if (email == "") { return  validation }

        if (email !== "" && !Patterns.EMAIL_ADDRESS.matcher(email!!).matches()) {
            return validation.apply { message = "Formato de correo incorrecto" }
        }

        if (phone == "" || address == "") { return validation }

        if  (phone!!.length != 9) {
            return validation.apply { message = "Formato de celular incorrecto" }
        }

        return ValidatorState(false)
    }

    private fun passwordValidator(): ValidatorState {
        val password = password.get()
        val confirmPassword = confirmPassword.get()

        val validation = ValidatorState(true)

        if (password == "") { return validation }

        if  (password != "" && password?.length!! < 6) {
            return validation.apply { message = "La contraseña debe tener como mínimo de 6 caracteres" }
        }

        val numRegex = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)\$".toRegex()
        val alphaRegex = ".*[A-Z].*".toRegex()

        if  ((!password.matches(numRegex)) || (!confirmPassword!!.matches(numRegex))) {
            return validation.apply { message = "Las contraseña tiene que ser alfanumérica" }
        }

        if (password != confirmPassword) {
            return validation.apply { message = "Las contraseñas deben coincidir" }
        }

        return ValidatorState(false)
    }

    fun register() {

        val validator = passwordValidator()

        if  (!validator.status) {
            startJob {
                repository.createClientAccount(
                    loadedPicture.get(),
                    name = name.get()!!,
                    surname = surname.get()!!,
                    gender = gender.get()!!,
                    documentType = documentType.get()!!,
                    documentNumber = documentNumber.get()!!,
                    age = age.get()!!.toInt(),
                    email = email.get()!!,
                    phone = phone.get()!!,
                    address = address.get()!!,
                    addressReference = addressReference.get(),
                    password = password.get()!!
                )

                getNavigator().showPasswordView()
            }
        } else {
            _showMessage.value = validator.message?.let { it } ?: run { "Por favor complete los campos vacíos" }
        }
    }

    fun resetMessage() {
        _showMessage.value = ""
    }
}

data class ValidatorState(val status: Boolean, var message: String? = null)