package com.peruapps.icnclient.features.account.presentation.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.account.data.LoginRepository
import com.peruapps.icnclient.features.account.presentation.views.LoginNavigator
import com.peruapps.icnclient.ui.base.BaseViewModel

class LoginViewModel (private val repository: LoginRepository): BaseViewModel<LoginNavigator>() {

    var email = ObservableField<String>("")
    var password = ObservableField<String>("")

    private val _validationMessage = MutableLiveData<Int>()
    val validationMessage: LiveData<Int>
        get() = _validationMessage

    fun signIn() {
        if (validator()) {
            startJob {
                repository.login(email.get()!!, password.get()!!)
                getNavigator().redirectAfterLogin()
            }
        }
    }

    fun facebookSignIn(email: String, name: String) {
        startJob {
            val response = repository.emailValidation(email)
            if (response.status) {
                getNavigator().redirectAfterLogin()
            } else {
                getNavigator().showCreateAccountView(email, name)
            }
        }
    }

    fun forgotPasswordView() {
        getNavigator().showForgotPasswordView()
    }

    private fun validator(): Boolean {
        val email = email.get()
        val password = password.get()

        if (email == "" || password == "") {
            _validationMessage.value = R.string.validation_empty
            return false
        }

        if (email != "" && !Patterns.EMAIL_ADDRESS.matcher(email!!).matches()) {
            _validationMessage.value = R.string.validation_email
            return false
        }

        return true
    }
}