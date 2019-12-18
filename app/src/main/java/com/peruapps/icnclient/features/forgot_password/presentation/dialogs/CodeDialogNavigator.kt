package com.peruapps.icnclient.features.forgot_password.presentation.dialogs

import com.peruapps.icnclient.model.response.PasswordResetTokenResponse

interface CodeDialogNavigator {
    fun showResetPasswordView(response: PasswordResetTokenResponse)
}