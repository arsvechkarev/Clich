package com.arsvechkarev.core

import android.view.View
import androidx.fragment.app.Fragment

/**
 * Bridge that fragment can access functionality of main activity through
 */
interface CoreActivity {
  
  /**
   * Goes to a [fragment] from root layout
   */
  fun goToFragmentFromRoot(fragment: Fragment, addToBackStack: Boolean = false)
  
  /**
   * Anchor view for snackbar
   */
  val snackBarPlace: View
}

val Fragment.coreActivity
  get() = (activity as CoreActivity)