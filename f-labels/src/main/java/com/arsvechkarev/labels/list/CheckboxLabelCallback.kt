package com.arsvechkarev.labels.list

import com.arsvechkarev.core.domain.model.Label

interface CheckboxLabelCallback {
  
  fun onCheck(label: Label)
  fun onUncheck(label: Label)
}