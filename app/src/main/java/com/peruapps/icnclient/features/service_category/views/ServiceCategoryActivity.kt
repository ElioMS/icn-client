package com.peruapps.icnclient.features.service_category.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.services.views.ServiceActivity
import com.peruapps.icnclient.helpers.NavigationHelper
import kotlinx.android.synthetic.main.activity_service_category.*

class ServiceCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_category)

        initActivityEvents()
    }

    private fun initActivityEvents() {
        iv_back.setOnClickListener { super.onBackPressed() }
        iv_item.setOnClickListener { NavigationHelper.redirecToWithData(this, ServiceActivity::class.java, 1) }
        iv_technical.setOnClickListener { NavigationHelper.redirecToWithData(this, ServiceActivity::class.java, 2) }
    }
}
