package com.peruapps.icnclient.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.peruapps.icnclient.db.sharePreferences.PowerPreferences
import com.peruapps.icnclient.db.sharePreferences.PreferencesManager
import com.peruapps.icnclient.features.account.data.LoginRepository
import com.peruapps.icnclient.features.account.data.LoginRepositoryImpl
import com.peruapps.icnclient.features.account.presentation.viewmodel.AccountViewModel
import com.peruapps.icnclient.features.account.presentation.viewmodel.ForgotPasswordViewModel
import com.peruapps.icnclient.features.account.presentation.viewmodel.LoginViewModel
import com.peruapps.icnclient.features.calendar.presentation.CalendarViewModel
import com.peruapps.icnclient.features.change_password.data.ChangePasswordRepository
import com.peruapps.icnclient.features.change_password.data.ChangePasswordRepositoryImpl
import com.peruapps.icnclient.features.change_password.presentation.ChangePasswordViewModel
import com.peruapps.icnclient.features.edit_profile.data.EditProfileRepository
import com.peruapps.icnclient.features.edit_profile.data.EditProfileRepositoryImpl
import com.peruapps.icnclient.features.edit_profile.presentation.EditProfileViewModel
import com.peruapps.icnclient.features.notifications.data.NotificationRepository
import com.peruapps.icnclient.features.notifications.data.NotificationRepositoryImpl
import com.peruapps.icnclient.features.notifications.presentation.NotificationViewModel
import com.peruapps.icnclient.features.nursing_staff.presentation.NursingStaffViewModel
import com.peruapps.icnclient.features.profile.presentation.ProfileViewModel
import com.peruapps.icnclient.features.reservations.data.AppointmentRepository
import com.peruapps.icnclient.features.reservations.data.AppointmentRepositoryImpl
import com.peruapps.icnclient.features.reservations.presentation.viewmodel.AppointmentViewModel
import com.peruapps.icnclient.features.schedule_dates.presentation.ScheduleDatesViewModel
import com.peruapps.icnclient.features.services.data.ServiceRepository
import com.peruapps.icnclient.features.services.data.ServiceRepositoryImpl
import com.peruapps.icnclient.features.service_type.presentation.ServiceTypeViewModel
import com.peruapps.icnclient.features.services.viewmodel.ServiceViewModel
import com.peruapps.icnclient.features.substances.data.SubstanceRepository
import com.peruapps.icnclient.features.substances.data.SubstanceRepositoryImpl
import com.peruapps.icnclient.features.substances.presentation.SubstancesViewModel
import com.peruapps.icnclient.features.substances.presentation.dialogs.SubstanceDialogViewModel
import com.peruapps.icnclient.features.summary.presentation.SummaryViewModel
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
            .baseUrl("http://192.168.1.133:8000/api/v1/")
            .build()
    }
}

val apiModule = module {
    single { get<Retrofit>().create(ApiService::class.java) }
}

val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
    single<AppointmentRepository> { AppointmentRepositoryImpl(get()) }
    single<ServiceRepository> { ServiceRepositoryImpl(get()) }
    single<SubstanceRepository> { SubstanceRepositoryImpl(get()) }
    single<NotificationRepository> { NotificationRepositoryImpl(get()) }
    single<EditProfileRepository> { EditProfileRepositoryImpl(get()) }
    single<ChangePasswordRepository> { ChangePasswordRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { AccountViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ForgotPasswordViewModel() }
    viewModel { AppointmentViewModel(get()) }
    viewModel { ServiceViewModel(get()) }
    viewModel { ServiceTypeViewModel() }
    viewModel { ProfileViewModel(get()) }
    viewModel { CalendarViewModel() }
    viewModel { ScheduleDatesViewModel() }
    viewModel { SubstancesViewModel(get()) }
    viewModel { NursingStaffViewModel() }
    viewModel { SubstanceDialogViewModel(get()) }
    viewModel { SummaryViewModel() }
    viewModel { NotificationViewModel(get()) }
    viewModel { EditProfileViewModel(get(), get()) }
    viewModel { ChangePasswordViewModel(get()) }
}

val icnModules = listOf(
    applicationModule,
    retrofitModules,
    repositoryModule,
    apiModule,
    viewModelModule
)