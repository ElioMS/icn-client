package com.peruapps.icnclient.features.account.presentation.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.account.presentation.viewmodel.AccountViewModel
import com.peruapps.icnclient.features.account.presentation.views.fragments.AccountFragment
import com.peruapps.icnclient.features.account.presentation.views.fragments.LoginFragment
import com.peruapps.icnclient.features.reservations.presentation.views.ReservationActivity
import com.peruapps.icnclient.helpers.NavigationHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountActivity : AppCompatActivity(), AccountNavigator {

    private val model: AccountViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        model.setNavigator(this)
        model.authenticationCheck()
    }


    override fun showLoginView() {
        NavigationHelper.redirectTo(this, ReservationActivity::class.java, true)
    }

    override fun showAccountView() {
        NavigationHelper.changeFragment(supportFragmentManager,
            R.id.main_container,
            AccountFragment(),
            "AccountFragment") //To change body of created functions use File | Settings | File Templates.
    }

}
