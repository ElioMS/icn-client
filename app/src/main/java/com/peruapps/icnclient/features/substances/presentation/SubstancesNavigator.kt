package com.peruapps.icnclient.features.substances.presentation

import com.peruapps.icnclient.model.SubstanceDetail

interface SubstancesNavigator {
    fun openSubstanceDialog()
    fun updateDetail(data: SubstanceDetail)
}