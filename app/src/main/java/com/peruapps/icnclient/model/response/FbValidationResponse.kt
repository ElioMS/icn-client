package com.peruapps.icnclient.model.response

data class FbValidationResponse (val status: Boolean,
                                 val data: LoginResponse? = null)