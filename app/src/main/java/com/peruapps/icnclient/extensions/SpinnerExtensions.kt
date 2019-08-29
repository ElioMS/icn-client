package com.peruapps.icnclient.extensions

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

/**
 * Created by Christopher Elias on 12/08/2019.
 * celias@peruapps.com.pe
 *
 * Peru Apps
 * Lima, Peru.
 **/

/**
 * react only to when an item has been selected and
 * pass the position of it.
 *
 * @param callback interface that reacts when spinner item has been selected.
 */
inline fun Spinner.onItemSelected(crossinline callback: (Int)-> Unit ) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            callback(position)
        }
        override fun onNothingSelected(p0: AdapterView<*>?) { /*Ignore this*/ }
    }
}