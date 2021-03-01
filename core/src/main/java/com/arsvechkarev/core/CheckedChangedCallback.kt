package com.arsvechkarev.core

import com.arsvechkarev.core.domain.model.Label

interface CheckedChangedCallback {
  
  fun onLabelSelected(label: Label)
  
  fun onLabelUnselected(label: Label)
}