package com.peruapps.icnclient.ui.uimodels

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.peruapps.icnclient.BR

class Auth(var email: String, var password: String)
//class Auth(email: String, var password: String): BaseObservable() {
//
//    @get:Bindable
//    var email: String = ""
//    set(value) {
//        field = value
//        notifyPropertyChanged(BR.email)
//    }
//
//}
