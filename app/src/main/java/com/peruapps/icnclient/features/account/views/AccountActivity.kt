package com.peruapps.icnclient.features.account.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.account.views.fragments.AccountFragment

class AccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_container, AccountFragment(), "AccountFragment")
        fragmentTransaction.commit()
    }
}
