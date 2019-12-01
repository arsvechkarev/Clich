package com.arsvechkarev.core

import android.view.View
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

/**
 * Bridge that fragment can access functionality of main activity with
 */
interface CoreActivity {
  
  /**
   * Anchor view for snackbar
   */
  val snackBarPlace: View
  
  /**
   * Goes to a [fragment] from root layout
   */
  fun <T : Fragment> goToFragmentFromRoot(
    fragment: Fragment,
    fragmentClass: KClass<T>,
    addToBackStack: Boolean = false
  )
  
  /**
   * Subscribe for every change in fragment back stack. See [BaseFragment.onBackStackUpdate]
   */
  fun <T : BaseFragment> subscribeOnBackStackChanges(fragment: T)
}

val Fragment.coreActivity
  get() = (activity as CoreActivity)