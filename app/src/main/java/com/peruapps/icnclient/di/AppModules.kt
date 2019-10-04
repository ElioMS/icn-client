package com.peruapps.icnclient.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.peruapps.icnclient.BuildConfig
import com.peruapps.icnclient.R
import com.peruapps.icnclient.db.sharePreferences.PowerPreferences
import com.peruapps.icnclient.db.sharePreferences.PreferencesManager
import com.peruapps.icnclient.features.account.data.LoginRepository
import com.peruapps.icnclient.features.account.data.LoginRepositoryImpl
import com.peruapps.icnclient.features.account.presentation.viewmodel.ForgotPasswordViewModel
import com.peruapps.icnclient.features.account.presentation.viewmodel.LoginViewModel
import com.peruapps.icnclient.network.ApiService
import com.peruapps.icnclient.network.BasicAuthInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val applicationModule = module {
    single { PowerPreferences(androidContext()) }
    single { PreferencesManager(get()) }

    single<Gson> { GsonBuilder().create() }
}


val retrofitModules = module {
    single { Cache(androidContext().cacheDir, 50 * 1024 * 1024) }

    single { (loggingLevel: HttpLoggingInterceptor.Level) ->
        HttpLoggingInterceptor().apply {
            level = loggingLevel
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(get()))
            .cache(get())
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }


    single {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.1.26:8000/api/v1/")
            .build()
    }
}

val apiModule = module {
    single { get<Retrofit>().create(ApiService::class.java) }
}

val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { ForgotPasswordViewModel() }
}

val icnModules = listOf(
    applicationModule,
    retrofitModules,
    repositoryModule,
    apiModule,
    viewModelModule
)