package com.arsvechkarev.clich.presentation

import com.arsvechkarev.core.domain.model.Label

sealed class MainScreenState {
  
  class LoadedLabels(val labels: List<Label>) : MainScreenState()
  
  object NoLabels : MainScreenState()
}