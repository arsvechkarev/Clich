package com.arsvechkarev.labels.presentation

import com.arsvechkarev.core.domain.model.Label

interface LabelCheckedCallback {
  
  fun isLabelChecked(label: Label): Boolean
  
  fun onLabelChecked(label: Label)
  
  fun onLabelUnchecked(label: Label)
}