package ru.kesva.makechoice.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified T: ViewModel> getViewModel(
    factory: ViewModelProvider.Factory, viewModelStoreOwner: ViewModelStoreOwner
): T {
    return ViewModelProvider(viewModelStoreOwner, factory)[T::class.java]
}

