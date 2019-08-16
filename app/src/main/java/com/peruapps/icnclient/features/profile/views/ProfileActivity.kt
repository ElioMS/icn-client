package com.peruapps.icnclient.features.profile.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.peruapps.icnclient.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initActivityEvents()
    }

    private fun initActivityEvents() {
        iv_close.setOnClickListener { onBackPressed() }
    }
}
