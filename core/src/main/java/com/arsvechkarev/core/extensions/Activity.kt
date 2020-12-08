package com.arsvechkarev.core.extensions

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arsvechkarev.core.BaseFragment
import kotlin.reflect.KClass

inline fun <reified T : Fragment> AppCompatActivity.switchToFragment(
  @IdRes contentResId: Int,
  fragment: T,
  addToBackStack: Boolean = false
) {
  val transaction = supportFragmentManager.beginTransaction()
    .replace(contentResId, fragment, T::class.java.simpleName)
  if (addToBackStack) transaction.addToBackStack(null)
  transaction.commit()
}

fun <T : Fragment> AppCompatActivity.findFragment(fragmentClass: KClass<T>): BaseFragment? {
  val fragment = supportFragmentManager.findFragmentByTag(fragmentClass.java.simpleName)
  if (fragment != null && fragment.isVisible) return fragment as BaseFragment?
  return null
}