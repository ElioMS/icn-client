package com.peruapps.icnclient.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.peruapps.icnclient.R
import com.peruapps.icnclient.ui.fragments.splash.SplashFragment

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_container, SplashFragment(), "SplashFragment")
        fragmentTransaction.commit()
    }
}
