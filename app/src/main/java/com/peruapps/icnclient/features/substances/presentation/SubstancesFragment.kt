package com.peruapps.icnclient.features.substances.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.peruapps.icnclient.BR

import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentSubstanceBinding
import com.peruapps.icnclient.features.substances.presentation.dialogs.SubstanceDialog
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.model.SubstanceDetail
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubstancesFragment : Fragment(), SubstancesNavigator {

    private lateinit var binding: FragmentSubstanceBinding
    val model: SubstancesViewModel by viewModel()

    var categoryId: Int = 0
    private lateinit var service: Service
    private lateinit var serviceType: ServiceType
    var quantity: Int = 0

    companion object {
        fun setData(categoryId: Int, service: Service, serviceType: ServiceType, quantity: Int) = SubstancesFragment().apply {
            this.categoryId = categoryId
            this.service = service
            this.serviceType = serviceType
            this.quantity = quantity
            Log.d("quantity", quantity.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_substance, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, model)
        model.setNavigator(this)
        model.substanceQuantity.set(quantity)

        model.addSubstanceItems()
    }

    override fun openSubstanceDialog() {
        val dialog = SubstanceDialog.setData(this)
        dialog.show(fragmentManager!!, "SUBSTANCE_DIALOG")
    }

    override fun updateDetail(data: SubstanceDetail) {
        val position = model.currentPosition.get()

        position?.let {
            Log.d("dialog_data", it.toString())
            model.detailAdapter.items[it] = data
            model.detailAdapter.notifyDataSetChanged()
        }
    }
}
