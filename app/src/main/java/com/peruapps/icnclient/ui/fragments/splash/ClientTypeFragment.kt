package com.peruapps.icnclient.ui.fragments.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.peruapps.icnclient.R
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.features.account.views.AccountActivity
import kotlinx.android.synthetic.main.fragment_client_type.*

class ClientTypeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        icEmpresa.setOnClickListener {
            NavigationHelper.redirectTo(activity!!, AccountActivity::class.java )
        }
    }

}
