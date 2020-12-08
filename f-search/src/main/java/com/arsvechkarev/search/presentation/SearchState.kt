package com.arsvechkarev.search.presentation

import com.arsvechkarev.core.domain.model.Word

sealed class SearchState {
  
  class FoundWords(val words: List<Word>) : SearchState()
  
  class DisplayingAllWords(val words: List<Word>) : SearchState()
}