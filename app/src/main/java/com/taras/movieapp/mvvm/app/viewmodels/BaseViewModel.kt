package com.taras.movieapp.mvvm.app.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

open class BaseViewModel : ViewModel() {

    private val mJob = SupervisorJob()
    protected val mScope = CoroutineScope(Dispatchers.Main + mJob)

    override fun onCleared() {
        super.onCleared()
        mScope.coroutineContext.cancelChildren()
    }
}