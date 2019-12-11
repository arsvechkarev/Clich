package com.arsvechkarev.testui

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun background(block: suspend () -> Unit) {
  GlobalScope.launch(Dispatchers.IO) {
    block()
  }
}
