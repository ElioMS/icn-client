package com.peruapps.icnclient.features.account.presentation.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import com.peruapps.icnclient.features.account.data.LoginRepository
import com.peruapps.icnclient.features.account.presentation.views.LoginNavigator
import com.peruapps.icnclient.ui.base.BaseViewModel

class LoginViewModel (private val repository: LoginRepository): BaseViewModel<LoginNavigator>() {

    var email = ObservableField<String>("")
    var password = ObservableField<String>("")

    fun signIn() {
        startJob {
            repository.login(email.get()!!, password.get()!!)
            getNavigator().redirectAfterLogin()
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

}