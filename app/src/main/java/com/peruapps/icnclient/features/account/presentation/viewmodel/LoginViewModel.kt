package com.peruapps.icnclient.features.account.presentation.viewmodel

import android.util.Log
import com.peruapps.icnclient.features.account.data.LoginRepository
import com.peruapps.icnclient.features.account.presentation.views.LoginNavigator
import com.peruapps.icnclient.ui.base.BaseViewModel

class LoginViewModel (private val repository: LoginRepository): BaseViewModel<LoginNavigator>() {

    fun signIn() {

//        requestLoading.set(true)

        startJob {
            val response = repository.login("egarcia@peruapps.com.pe", "123123")
            getNavigator().redirectAfterLogin()
        }
    }

    fun forgotPasswordView() {
        getNavigator().showForgotPasswordView()
    }

}