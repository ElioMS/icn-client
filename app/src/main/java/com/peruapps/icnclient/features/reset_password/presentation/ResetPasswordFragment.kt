package com.peruapps.icnclient.features.reset_password.presentation

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
import com.peruapps.icnclient.databinding.FragmentResetPasswordBinding
import com.peruapps.icnclient.features.reservations.presentation.views.ReservationActivity
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.response.PasswordResetTokenResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordFragment : Fragment(), ResetPasswordNavigator {

    private lateinit var binding: FragmentResetPasswordBinding
    val model: ResetPasswordViewModel by viewModel()

    private lateinit var response: PasswordResetTokenResponse

    companion object {
        fun setData(response: PasswordResetTokenResponse) = ResetPasswordFragment().apply {
            this.response = response
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_password, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.token.set(response.token)
        model.email.set(response.email)
        model.setNavigator(this)
        binding.setVariable(BR.viewModel, model)
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        model.validationMessage.observe(this, Observer {
            Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
//            Log.d("validation", it)
        })
    }

    override fun onSuccessResponse() {
        NavigationHelper.redirectTo(activity!!, ReservationActivity::class.java, true)
    }
}
