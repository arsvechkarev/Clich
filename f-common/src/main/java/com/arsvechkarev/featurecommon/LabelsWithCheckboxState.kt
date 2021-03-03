package com.arsvechkarev.featurecommon

import com.arsvechkarev.core.domain.model.Label

sealed class LabelsWithCheckboxState {
  
  object ShowingNoLabelsWithCheckbox : LabelsWithCheckboxState()
  
  class ShowingLabelsWithCheckbox(val allLabels: List<Label>) : LabelsWithCheckboxState()
}