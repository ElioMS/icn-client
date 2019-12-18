package com.peruapps.icnclient.model.request

data class ResetPasswordRequest(val token: String,
                                val email: String,
                                val password: String)