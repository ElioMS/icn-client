package com.peruapps.icnclient.model

data class Appointment (val id: Int,
                        val title: String, // Service name
                        val subtitle: String, // Service type
                        val address: String,
                        val time: String,
                        val turn: String,
                        var category: Int? = null)