package com.peruapps.icnclient.features.account.presentation.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.peruapps.icnclient.BR

import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentLoginBinding
import com.peruapps.icnclient.features.account.presentation.viewmodel.LoginViewModel
import com.peruapps.icnclient.features.account.presentation.views.LoginNavigator
import com.peruapps.icnclient.features.reservations.presentation.views.ReservationActivity
import com.peruapps.icnclient.helpers.NavigationHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(), LoginNavigator {

    private lateinit var binding: FragmentLoginBinding
    private val model: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.setNavigator(this)
        subscribeLiveData()
        binding.setVariable(BR.viewModel, model)
    }

    override fun redirectAfterLogin() {
        NavigationHelper.redirectTo(activity!!, ReservationActivity::class.java, true)
    }

    override fun showForgotPasswordView() {
        NavigationHelper.changeFragment(fragmentManager!!,
            R.id.main_container,
            ForgotPasswordFragment(),
            "ForgotPasswordFragment")
    }

    private fun subscribeLiveData() {
        model.showError.observe(this, Observer {
            if  (it != "") {
                Toast.makeText(context!!, it , Toast.LENGTH_SHORT).show()
            }
        })
    }
}
