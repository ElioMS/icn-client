package com.peruapps.icnclient.features.account.presentation.viewmodel

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

    fun forgotPasswordView() {
        getNavigator().showForgotPasswordView()
    }

}