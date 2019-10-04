package com.peruapps.icnclient.features.account.presentation.viewmodel

import com.peruapps.icnclient.features.account.presentation.views.ForgotPasswordNavigator
import com.peruapps.icnclient.ui.base.BaseViewModel

class ForgotPasswordViewModel: BaseViewModel<ForgotPasswordNavigator>() {

    fun sendEmail() {
        getNavigator().sendEmail()
    }

    fun validateUserCode() {

    }

    fun resetPassword() {

    }

}