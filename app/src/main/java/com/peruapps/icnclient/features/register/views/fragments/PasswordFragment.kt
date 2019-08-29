package com.peruapps.icnclient.features.register.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.peruapps.icnclient.R
import kotlinx.android.synthetic.main.fragment_password.*
import androidx.lifecycle.ViewModelProviders
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.peruapps.icnclient.databinding.FragmentPasswordBinding
import com.peruapps.icnclient.features.register.viewmodel.RegisterViewModel
import org.json.JSONObject

class PasswordFragment : Fragment() {

    private lateinit var model: RegisterViewModel
//    lateinit var binding: FragmentPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this)[RegisterViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPasswordBinding = DataBindingUtil.inflate(inflater ,R.layout.fragment_password,container , false)
        val myView: View  = binding.root

        binding.viewModel = model

        return myView
//        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCreateAccount.setOnClickListener {
            model.password.set(et_password.text.toString())
            model.confirmPassword.set(et_confirm_password.text.toString())
            model.register()
        }

        initFragmentEvents()
    }

    private fun initFragmentEvents() {
        iv_back.setOnClickListener { activity?.onBackPressed() }
    }


}
