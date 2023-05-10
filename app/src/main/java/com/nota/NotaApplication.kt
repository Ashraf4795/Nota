package com.nota

import android.app.Application
import com.nota.base.di.ApplicationComponent
import com.nota.base.di.DaggerApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

const val TAG = "NotaApplication"

class NotaApplication : Application() {
    val applicationScope: CoroutineScope
    lateinit var applicationComponent: ApplicationComponent


    init {
        applicationScope = CoroutineScope(SupervisorJob())
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
    }

}