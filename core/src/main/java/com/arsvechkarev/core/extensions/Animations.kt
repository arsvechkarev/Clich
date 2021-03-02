package com.arsvechkarev.core.extensions

import android.animation.Animator
import android.graphics.drawable.Animatable
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

const val DURATION_SHORT = 170L
const val DURATION_DEFAULT = 300L
const val DURATION_MEDIUM = 500L
const val DURATION_LONG = 1000L

val AccelerateDecelerateInterpolator = AccelerateDecelerateInterpolator()

fun View.animateVisible(andThen: () -> Unit = {}, duration: Long = DURATION_DEFAULT) {
  alpha = 0f
  visible()
  animate().alpha(1f).setDuration(duration)
    .setInterpolator(AccelerateDecelerateInterpolator)
    .withEndAction(andThen)
    .start()
}

fun View.animateInvisible(andThen: () -> Unit = {}, duration: Long = DURATION_DEFAULT) {
  animate().alpha(0f).setDuration(duration)
    .setInterpolator(AccelerateDecelerateInterpolator)
    .withEndAction {
      invisible()
      andThen()
    }
    .start()
}

fun View.animateGone(andThen: () -> Unit = {}, duration: Long = DURATION_DEFAULT) {
  animate().alpha(0f).setDuration(duration)
    .setInterpolator(AccelerateDecelerateInterpolator)
    .withEndAction {
      gone()
      andThen()
    }
    .start()
}

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