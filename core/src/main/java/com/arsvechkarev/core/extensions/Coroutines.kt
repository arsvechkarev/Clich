package com.arsvechkarev.core.extensions

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun inBackground(block: suspend () -> Unit) {
  GlobalScope.launch {
    block()
  }
}