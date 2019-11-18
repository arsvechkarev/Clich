package com.arsvechkarev.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch

/**
 * Base view model to facilitate work with coroutines
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseViewModel(
  dispatcherProvider: DispatcherProvider = DispatcherProvider.DefaultImpl
) : ViewModel(), CoroutineScope {
  
  override val coroutineContext: CoroutineDispatcher = dispatcherProvider.Main
  val jobs: MutableList<Job> = ArrayList()
  
  fun launchCoroutine(block: suspend CoroutineScope.() -> Unit) {
    jobs.add(launch(coroutineContext) { block() })
  }
  
  fun launchGlobal(block: suspend CoroutineScope.() -> Unit) {
    launch(coroutineContext) { block() }
  }
  
  fun cancelAllCoroutines() {
    for (job in jobs) {
      job.cancel()
    }
  }
  
  suspend fun cancelAllAndJoin() {
    for (job in jobs) {
      job.cancelAndJoin()
    }
  }
  
  override fun onCleared() {
    cancelAllCoroutines()
  }
}