package com.arsvechkarev.core

import androidx.lifecycle.ViewModel
import com.arsvechkarev.core.DispatcherProvider.DefaultImpl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Base view model to facilitate work with coroutines
 */
abstract class BaseViewModel(
  protected val dispatcherProvider: DispatcherProvider = DefaultImpl
) : ViewModel() {
  
  protected suspend fun <T> onIoThread(
    block: suspend () -> T
  ) = withContext(dispatcherProvider.IO) {
    return@withContext block()
  }
  
  protected fun coroutine(block: suspend () -> Unit) {
    GlobalScope.launch(dispatcherProvider.Main) { block() }
  }
}