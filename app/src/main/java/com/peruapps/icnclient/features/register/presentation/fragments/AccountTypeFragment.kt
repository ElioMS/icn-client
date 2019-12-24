package com.peruapps.icnclient.features.register.presentation.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.account.presentation.views.AccountActivity
import com.peruapps.icnclient.features.register.presentation.dialogs.DialogRegister
import com.peruapps.icnclient.helpers.NavigationHelper
import kotlinx.android.synthetic.main.fragment_account_type.*

class AccountTypeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvents()
    }

    private fun initEvents() {
        ivPersonal.setOnClickListener { showCreateAccountView() }
        ivEmpresa.setOnClickListener { showDialog() }
    }

    private fun showDialog() {
        val dialog = DialogRegister(context!!, R.style.FullScreenDialogStyle, ::showAccountView)
        dialog.show()
    }

    private fun showAccountView() {
        NavigationHelper.redirectTo(activity!!, AccountActivity::class.java, true)
    }

    private fun showCreateAccountView() {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction!!.setCustomAnimations(
            R.anim.transition_slide_right_in,
            R.anim.transition_slide_left_out,
            android.R.anim.slide_in_left,
            R.anim.transition_slide_right_out
        )

        fragmentTransaction.replace(R.id.main_container, CreateAccountFragment(), "CreateAccountFragment")
        fragmentTransaction.addToBackStack("CreateAccountFragment")
        fragmentTransaction.commit()
    }

}
