package com.peruapps.icnclient.model.response

class ErrorResponse(val error: Int, val description: String): Exception(description)