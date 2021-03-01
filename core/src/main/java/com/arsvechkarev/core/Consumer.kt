package com.arsvechkarev.core

fun interface Consumer<T> {
  
  fun accept(t: T)
}