package com.peruapps.icnclient.model

data class ServiceType(val id: Int, val name: String, val description: String?, var price: Float?) {

    fun formatPrice(): String {
        return "%.2f".format(price)
    }

}