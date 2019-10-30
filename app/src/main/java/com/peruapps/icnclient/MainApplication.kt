package com.peruapps.icnclient

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.peruapps.icnclient.di.icnModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)

        startKoin {
            androidContext(this@MainApplication)
            modules(icnModules)
        }
    }
}