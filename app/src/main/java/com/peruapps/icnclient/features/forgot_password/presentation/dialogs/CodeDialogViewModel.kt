package com.peruapps.icnclient.features.forgot_password.presentation.dialogs

import android.util.Log
import androidx.databinding.ObservableField
import com.peruapps.icnclient.features.forgot_password.data.ForgotPasswordRepository
import com.peruapps.icnclient.ui.base.BaseViewModel

class CodeDialogViewModel(private val repository: ForgotPasswordRepository) : BaseViewModel<CodeDialogNavigator>() {

    val code = ObservableField<String>()

    private fun validator() {

    }

    fun onClickSendButton() {
        startJob {
            val response = repository.tokenValidation(code.get()!!)
            getNavigator().showResetPasswordView(response)
            Log.d("token_validation", response.toString())
        }
    }
}