package com.nota.base.di

import com.nota.NotaApplication
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
class CoroutineModule {

    @Provides
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @ApplicationScope
    fun provideApplicationCoroutineScope(application: NotaApplication): CoroutineScope {
        return application.applicationScope
    }
}