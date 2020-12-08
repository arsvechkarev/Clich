package com.arsvechkarev.core

import androidx.lifecycle.ViewModel
import com.arsvechkarev.core.DispatcherProvider.DefaultImpl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Base view model to facilitate work with coroutines
 */
abstract class BaseViewModel(
  private val dispatcherProvider: DispatcherProvider = DefaultImpl
) : ViewModel() {
  
  protected fun coroutine(block: suspend () -> Unit) {
    GlobalScope.launch(dispatcherProvider.Main) { block() }
  }
}