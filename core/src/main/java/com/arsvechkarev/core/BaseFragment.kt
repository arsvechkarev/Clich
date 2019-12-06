package com.arsvechkarev.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Base class for all fragments
 */
abstract class BaseFragment : Fragment() {
  
  abstract val layoutId: Int
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(layoutId, container, false)
  }
  
  /**
   * Invokes when user clicks back button. Returns true if was handled, false otherwise
   */
  open fun onBackPressed(): Boolean {
    return false
  }
  
  /**
   * Invokes with every change in back stack. Do not forget to invoke [CoreActivity.subscribeOnBackStackChanges]
   */
  open fun onBackStackUpdate() {}
}