package com.arsvechkarev.core.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

fun <T, R> LiveData<T>.map(mapper: (T) -> R): LiveData<R> {
  return Transformations.map(this, mapper)
}

fun <T> LiveData<T>.observeOnce(fragment: Fragment, block: (T) -> Unit) {
  observe(fragment, object : Observer<T> {
    override fun onChanged(t: T) {
      block(t)
      removeObserver(this)
    }
  })
}

inline fun <reified T : ViewModel> Fragment.viewModelOf(
  factory: ViewModelProvider.Factory,
  block: (T) -> Unit = {}
) = ViewModelProviders.of(this, factory).get(T::class.java).apply(block)

inline fun <reified T : ViewModel> AppCompatActivity.viewModelOf(
  factory: ViewModelProvider.Factory,
  block: (T) -> Unit = {}
) = ViewModelProviders.of(this, factory).get(T::class.java).apply(block)