package com.arsvechkarev.core

import androidx.fragment.app.Fragment
import com.arsvechkarev.core.domain.model.Word

interface Navigator {
  
  fun goToLabelsCheckboxFragment(word: Word?)
  
  fun goToInfoFragment(word: Word? = null)
}

val Fragment.navigator
  get() = (activity as Navigator)