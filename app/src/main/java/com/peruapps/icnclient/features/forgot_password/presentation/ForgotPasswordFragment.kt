package com.peruapps.icnclient.features.forgot_password.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentForgotPasswordBinding
import com.peruapps.icnclient.features.forgot_password.presentation.dialogs.CodeDialog
import com.peruapps.icnclient.features.reset_password.presentation.ResetPasswordFragment
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.response.PasswordResetTokenResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordFragment : Fragment(),
    ForgotPasswordNavigator {

    private val model: ForgotPasswordViewModel by viewModel()
    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.setNavigator(this)
        binding.setVariable(BR.viewModel, model)

        subscribeLiveData()
    }

    override fun sendEmail() {
        val dialog = CodeDialog.setData(this)
        dialog.show(fragmentManager!!, "CODE_DIALOG")
    }

    override fun showResetPasswordView(response: PasswordResetTokenResponse) {
        NavigationHelper.changeFragment(fragmentManager!!,
            R.id.main_container,
            ResetPasswordFragment.setData(response),
            "ResetPasswordFragment")
    }

    private fun subscribeLiveData() {
        model.validationMessage.observe(this, Observer {
            Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
        })

        model.showError.observe(this, Observer {
            if  (it != "") {
                Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
