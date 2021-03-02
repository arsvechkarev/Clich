package com.arsvechkarev.info.presentation

import com.arsvechkarev.core.domain.model.Word

sealed class InfoState {
  
  object NewWordState : InfoState()
  
  class ExistingWordState(val word: Word) : InfoState()
  
  class OnAddLabelsClicked(val word: Word?) : InfoState()
}