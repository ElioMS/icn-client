package com.peruapps.icnclient.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Christopher Elias on 29/08/2019.
 * celias@peruapps.com.pe
 *
 * Peru Apps
 * Lima, Peru.
 **/

fun Long.toDate() : String{
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())//  Result: 25/09/2018
    val resultD = Date(this)
    return sdf.format(resultD)
}