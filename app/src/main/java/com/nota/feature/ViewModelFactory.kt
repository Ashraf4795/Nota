package com.nota.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val creators:  @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UnChecked_Cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var viewModelProvider: Provider<out ViewModel>? = creators[modelClass]
        if (viewModelProvider == null) {
           for ((viewModel, provider) in creators) {
               if (viewModel.isAssignableFrom(modelClass)) {
                   viewModelProvider = provider
                   break
               }
           }
        }

        if (viewModelProvider == null) {
            throw IllegalArgumentException("Unknown class $modelClass")
        }
        return viewModelProvider.get() as T
    }
}