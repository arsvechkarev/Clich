package com.arsvechkarev.words.presentation

import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word

sealed class WordsListState {
  
  class ShowingWords(val words: List<Word>) : WordsListState()
  
  class ShowingWordsForLabel(val label: Label, val words: List<Word>) : WordsListState()
}