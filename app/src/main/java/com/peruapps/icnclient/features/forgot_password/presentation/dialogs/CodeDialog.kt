package com.peruapps.icnclient.features.forgot_password.presentation.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.CustomDialogCodeBinding
import com.peruapps.icnclient.features.forgot_password.presentation.ForgotPasswordNavigator
import com.peruapps.icnclient.model.response.PasswordResetTokenResponse
import kotlinx.android.synthetic.main.custom_dialog_code.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CodeDialog : DialogFragment(), CodeDialogNavigator {

    private lateinit var binding: CustomDialogCodeBinding
    val model: CodeDialogViewModel by viewModel()

    private lateinit var navigator: ForgotPasswordNavigator

    companion object {
        fun setData(navigator: ForgotPasswordNavigator) = CodeDialog().apply {
            this.navigator = navigator
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_dialog_code, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.setNavigator(this)
        binding.setVariable(BR.viewModel, model)

        model.validationMessage.observe(this, Observer {
            Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
        })

        model.showError.observe(this, Observer {
            if  (it != "") {
                Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
            }
        })
    }



    override fun showResetPasswordView(response: PasswordResetTokenResponse) {
        this.dismiss()
        navigator.showResetPasswordView(response)
    }
}
