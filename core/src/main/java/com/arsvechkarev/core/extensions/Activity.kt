package com.arsvechkarev.core.extensions

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.extensions.Operation.ADD
import com.arsvechkarev.core.extensions.Operation.REPLACE
import kotlin.reflect.KClass

enum class Operation {
  ADD, REPLACE
}

inline fun <reified T : Fragment> AppCompatActivity.switchToFragment(
  @IdRes contentResId: Int,
  fragment: T,
  operation: Operation,
  addToBackStack: Boolean = false
) {
  val transaction = supportFragmentManager.beginTransaction()
  when (operation) {
    ADD -> transaction.add(contentResId, fragment, T::class.java.simpleName)
    REPLACE -> transaction.replace(contentResId, fragment, T::class.java.simpleName)
  }
  if (addToBackStack) transaction.addToBackStack(null)
  transaction.commit()
}

fun <T : Fragment> AppCompatActivity.findFragment(fragmentClass: KClass<T>): BaseFragment? {
  val fragment = supportFragmentManager.findFragmentByTag(fragmentClass.java.simpleName)
  if (fragment != null && fragment.isVisible) return fragment as BaseFragment?
  return null
}