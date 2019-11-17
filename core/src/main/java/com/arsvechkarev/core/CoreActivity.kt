package com.arsvechkarev.core

import android.view.View
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

/**
 * Bridge that fragment can access functionality of main activity through
 */
interface CoreActivity {
  
  /**
   * Goes to a [fragment] from root layout
   */
  fun <T : Fragment> goToFragmentFromRoot(
    fragment: Fragment,
    fragmentClass: KClass<T>,
    addToBackStack: Boolean = false
  )
  
  /**
   * Anchor view for snackbar
   */
  val snackBarPlace: View
}

val Fragment.coreActivity
  get() = (activity as CoreActivity)