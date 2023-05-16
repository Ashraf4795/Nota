package com.nota.feature.di

import androidx.lifecycle.ViewModelProvider
import com.nota.feature.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ViewModelFactoryModule {

    @Binds
    @Singleton
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}