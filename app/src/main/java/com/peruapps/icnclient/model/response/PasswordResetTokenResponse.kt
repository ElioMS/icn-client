package com.peruapps.icnclient.model.response

data class PasswordResetTokenResponse (val email: String,
                                       val token: String)