package com.arsvechkarev.core.extensions

import android.view.Window
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arsvechkarev.core.BaseFragment
import kotlin.reflect.KClass

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

fun AppCompatActivity.goToFragment(
  @IdRes contentResId: Int,
  fragment: Fragment
) {
  val transaction = supportFragmentManager.beginTransaction()
    .add(contentResId, fragment, Fragment::class.java.simpleName)
  transaction.addToBackStack(Fragment::class.java.simpleName)
  transaction.commit()
}

fun <T : Fragment> AppCompatActivity.findFragment(fragmentClass: KClass<T>): BaseFragment? {
  return supportFragmentManager.findFragmentByTag(fragmentClass.java.simpleName) as BaseFragment?
}