package com.arsvechkarev.testui

import com.agoda.kakao.screen.Screen

inline fun <reified T : Screen<T>> screen(): T {
  return T::class.java
    .newInstance()
}
