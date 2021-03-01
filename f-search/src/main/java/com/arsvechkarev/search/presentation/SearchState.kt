package com.arsvechkarev.search.presentation

import com.arsvechkarev.core.domain.model.Word

sealed class SearchState {
  
  object NoWordsFound : SearchState()
  
  class FoundWords(val words: List<Word>, val searchedText: String) : SearchState()
  
  class DisplayingAllWords(val words: List<Word>) : SearchState()
}