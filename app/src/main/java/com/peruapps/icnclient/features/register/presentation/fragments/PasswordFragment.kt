package com.peruapps.icnclient.features.register.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.peruapps.icnclient.R
import kotlinx.android.synthetic.main.fragment_password.*
import com.peruapps.icnclient.BR

import com.peruapps.icnclient.databinding.FragmentPasswordBinding
import com.peruapps.icnclient.features.account.presentation.views.AccountActivity
import com.peruapps.icnclient.features.account.presentation.views.fragments.LoginFragment
import com.peruapps.icnclient.features.forgot_password.presentation.ForgotPasswordFragment
import com.peruapps.icnclient.features.register.presentation.RegisterNavigator
import com.peruapps.icnclient.features.register.presentation.RegisterViewModel
import com.peruapps.icnclient.features.reservations.presentation.views.ReservationActivity
import com.peruapps.icnclient.helpers.NavigationHelper
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PasswordFragment : Fragment(), RegisterNavigator {

    private val registerViewModel by sharedViewModel<RegisterViewModel>()
    private lateinit var binding: FragmentPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel.resetMessage()
        registerViewModel.clearHttpError()

        registerViewModel.setNavigator(this)
        binding.setVariable(BR.viewModel, registerViewModel)

//        btnCreateAccount.setOnClickListener {
//            model.password.set(et_password.text.toString())
//            model.confirmPassword.set(et_confirm_password.text.toString())
//            model.register()
//        }

        initFragmentEvents()
        subscribeLiveData()
    }

    override fun showPasswordView() {
        NavigationHelper.changeFragment(fragmentManager!!,
            R.id.main_container,
            LoginFragment(),
            "LoginFragment",
            false)
//        NavigationHelper.redirectTo(activity!!, AccountActivity::class.java, true)
    }

    private fun initFragmentEvents() {
        iv_back.setOnClickListener { activity?.onBackPressed() }
    }

    private fun subscribeLiveData() {
        registerViewModel.showMessage.observe(this , androidx.lifecycle.Observer {
            if  (it != "") {
                Toast.makeText(context!!, it , Toast.LENGTH_SHORT).show()
            }
        })

        registerViewModel.showError.observe(this, androidx.lifecycle.Observer {
            if  (it != "") {
                Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
