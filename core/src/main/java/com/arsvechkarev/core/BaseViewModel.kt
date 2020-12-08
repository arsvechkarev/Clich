package com.arsvechkarev.core

import androidx.lifecycle.ViewModel
import com.arsvechkarev.core.DispatcherProvider.DefaultImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * Base view model to facilitate work with coroutines
 */
abstract class BaseViewModel(
  dispatcherProvider: DispatcherProvider = DefaultImpl
) : ViewModel() {
  
  private val scope = CoroutineScope(SupervisorJob() + dispatcherProvider.Main)
  
  protected fun coroutine(block: suspend () -> Unit) {
    scope.launch { block() }
  }
  
  override fun onCleared() {
    super.onCleared()
    scope.cancel()
  }
}