package com.arsvechkarev.core.extensions

import android.util.Log
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

inline fun <reified T : Fragment> AppCompatActivity.switchFragment(
  @IdRes contentResId: Int,
  fragment: T,
  addToBackStack: Boolean = false
) {
  val transaction = supportFragmentManager.beginTransaction()
    .replace(contentResId, fragment, T::class.java.simpleName)
  if (addToBackStack) transaction.addToBackStack(null)
  transaction.commit()
}

inline fun <reified T : Fragment> AppCompatActivity.goToFragment(
  @IdRes contentResId: Int,
  fragment: T,
  addToBackStack: Boolean = false
) {
  val transaction = supportFragmentManager.beginTransaction()
    .add(contentResId, fragment, T::class.java.simpleName)
  if (addToBackStack) transaction.addToBackStack(null)
  transaction.commit()
}

fun <T : Fragment> AppCompatActivity.goToFragment(
  @IdRes contentResId: Int,
  fragment: Fragment,
  fragmentClass: KClass<T>,
  addToBackStack: Boolean = false
) {
  val transaction = supportFragmentManager.beginTransaction()
    .add(contentResId, fragment, fragmentClass.java.simpleName)
  if (addToBackStack) transaction.addToBackStack(null)
  transaction.commit()
  
}

fun <T : Fragment> AppCompatActivity.findFragment(fragmentClass: KClass<T>): BaseFragment? {
  val fragment = supportFragmentManager.findFragmentByTag(fragmentClass.java.simpleName)
  return fragment as BaseFragment?
}