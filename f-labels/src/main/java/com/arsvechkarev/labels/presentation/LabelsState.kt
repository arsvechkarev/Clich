package com.arsvechkarev.labels.presentation

import com.arsvechkarev.core.domain.model.Label

sealed class LabelsState {
  
  object ShowingNoLabels : LabelsState()
  
  class ShowingLabels(val labels: List<Label>, val createOrDeleteHappened: Boolean) : LabelsState()
}

sealed class LabelsCheckboxState {
  
  object ShowingNoLabels : LabelsCheckboxState()
  
  class ShowingLabels(val allLabels: List<Label>) : LabelsCheckboxState()
}