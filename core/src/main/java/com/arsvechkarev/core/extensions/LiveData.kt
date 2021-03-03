package com.arsvechkarev.core.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

fun <T, R> LiveData<T>.map(mapper: (T) -> R): LiveData<R> {
  return Transformations.map(this, mapper)
}

fun <T> MutableLiveData<out Collection<T>>.add(item: T) {
  (value as MutableCollection<T>).add(item)
  value = value
}

fun <T> MutableLiveData<out Collection<T>>.remove(item: T) {
  (value as MutableCollection<T>).remove(item)
  value = value
}

fun <T> MutableLiveData<out Collection<T>>.addAll(collection: Collection<T>) {
  (value as MutableCollection<T>).addAll(collection)
  value = value
}
