package com.peruapps.icnclient.features.forgot_password.presentation

import com.peruapps.icnclient.model.response.PasswordResetTokenResponse

interface ForgotPasswordNavigator {
    fun sendEmail()
    fun showResetPasswordView(response: PasswordResetTokenResponse)
}