package com.arsvechkarev.core.extensions

import android.animation.Animator
import android.graphics.drawable.Animatable
import android.view.animation.AccelerateDecelerateInterpolator

const val DURATION_SHORT = 170L
const val DURATION_DEFAULT = 300L
const val DURATION_MEDIUM = 500L
const val DURATION_LONG = 1000L

val AccelerateDecelerateInterpolator = AccelerateDecelerateInterpolator()

fun Animator.startIfNotRunning() {
  if (!isRunning) start()
}

fun Animator.cancelIfRunning() {
  if (isRunning) cancel()
}

fun Animatable.startIfNotRunning() {
  if (!isRunning) start()
}

fun Animatable.stopIfRunning() {
  if (isRunning) stop()
}