package com.peruapps.icnclient.features.services.views

import com.peruapps.icnclient.model.Service

interface ServiceListener {

    fun onClick(entity: Service)

    interface Endovenoso {
        fun onClickServiceType(id: Int, position: Int)
    }
}