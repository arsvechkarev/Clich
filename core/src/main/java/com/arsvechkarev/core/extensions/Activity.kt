package com.arsvechkarev.core.extensions

import android.view.Window
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.setFullScreen() {
  requestWindowFeature(Window.FEATURE_NO_TITLE)
  window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)
}

fun AppCompatActivity.switchFragment(
  @IdRes contentResId: Int,
  fragment: Fragment,
  addToBackStack: Boolean = false
) {
  val transaction = supportFragmentManager.beginTransaction()
    .replace(contentResId, fragment)
  if (addToBackStack) transaction.addToBackStack(null)
  transaction.commit()
}