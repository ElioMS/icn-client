package com.peruapps.icnclient.features.summary_detail.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.ActivitySummaryDetailBinding
import kotlinx.android.synthetic.main.services_navigation_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SummaryDetailActivity : AppCompatActivity() {

    val TAG = SummaryDetailActivity::class.java.simpleName

    val model: SummaryDetailViewModel by viewModel()
    private lateinit var binding: ActivitySummaryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.extras?.get("data").toString().toInt()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_summary_detail)
        binding.setVariable(BR.viewModel, model)
        binding.executePendingBindings()

        model.loadData(id)
        initEvents()
    }

    private fun initEvents() {
        ib_back.setOnClickListener { onBackPressed() }
    }
}
