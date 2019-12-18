package com.peruapps.icnclient.features.register.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.peruapps.icnclient.R
import kotlinx.android.synthetic.main.fragment_password.*
import androidx.lifecycle.ViewModelProviders
import com.peruapps.icnclient.BR

import com.peruapps.icnclient.databinding.FragmentPasswordBinding
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
        registerViewModel.setNavigator(this)
        binding.setVariable(BR.viewModel, registerViewModel)

//        btnCreateAccount.setOnClickListener {
//            model.password.set(et_password.text.toString())
//            model.confirmPassword.set(et_confirm_password.text.toString())
//            model.register()
//        }

        initFragmentEvents()
    }

    override fun showPasswordView() {
        NavigationHelper.redirectTo(activity!!, ReservationActivity::class.java, false)
    }

    private fun initFragmentEvents() {
        iv_back.setOnClickListener { activity?.onBackPressed() }
    }
}
