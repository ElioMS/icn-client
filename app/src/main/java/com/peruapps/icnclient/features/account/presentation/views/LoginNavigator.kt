package com.peruapps.icnclient.features.account.presentation.views

interface LoginNavigator {

    fun redirectAfterLogin()
    fun showForgotPasswordView()
    fun showCreateAccountView(email: String, name: String)
}