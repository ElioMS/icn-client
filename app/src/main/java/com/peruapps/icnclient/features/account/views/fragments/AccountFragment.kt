package com.peruapps.icnclient.features.account.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.peruapps.icnclient.R
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.features.register.views.RegisterActivity
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_log_in.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.main_container, LoginFragment(), "LoginFragment")
            fragmentTransaction?.addToBackStack("LoginFragment")
            fragmentTransaction?.commit()
        }

        btn_create_account.setOnClickListener { NavigationHelper.redirectTo(activity!!, RegisterActivity::class.java) }
    }


}
