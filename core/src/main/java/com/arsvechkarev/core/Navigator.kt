package com.arsvechkarev.core

import androidx.fragment.app.Fragment
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import kotlin.reflect.KClass

interface Navigator {
  
  fun goToLabelsCheckboxFragment(word: Word?)
  
  fun goToInfoFragment(word: Word?)
  
  /**
   * Goes to a [fragment] from root layout
   */
  fun <T : Fragment> goToFragment(
    fragment: Fragment,
    fragmentClass: KClass<T>,
    addToBackStack: Boolean = false
  )
  
  /**
   * Subscribe for every change in fragment back stack. See [BaseFragment.onBackStackUpdate]
   */
  fun <T : BaseFragment> subscribeOnBackStackChanges(fragment: T)
}

val Fragment.navigator
  get() = (activity as Navigator)