package com.peruapps.icnclient.model

data class SummaryServiceDetail (val service: Service?,
                                 val serviceType: ServiceType?,
                                 val data: ArrayList<SubstanceDetail>? = null)