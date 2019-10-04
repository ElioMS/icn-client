package com.peruapps.icnclient.features.account.presentation.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentForgotPasswordBinding
import com.peruapps.icnclient.features.account.presentation.viewmodel.ForgotPasswordViewModel
import com.peruapps.icnclient.features.account.presentation.views.ForgotPasswordNavigator
import com.peruapps.icnclient.features.account.presentation.views.dialogs.CodeDialog
import com.peruapps.icnclient.helpers.NavigationHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordFragment : Fragment(), ForgotPasswordNavigator {

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
    }

    override fun sendEmail() {
        val dialog = CodeDialog(context!!, this)
        dialog.show()
    }

    override fun showResetPasswordView() {
        NavigationHelper.changeFragment(fragmentManager!!,
            R.id.main_container,
            ResetPasswordFragment(),
            "ResetPasswordFragment")
    }
}
