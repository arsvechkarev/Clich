package com.arsvechkarev.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.CentralDatabase
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.search.presentation.SearchState.DisplayingAllWords
import com.arsvechkarev.search.presentation.SearchState.FoundWords
import com.arsvechkarev.search.presentation.SearchState.NoWordsFound
import javax.inject.Inject

class SearchViewModel @Inject constructor(
  private val database: CentralDatabase
) : BaseViewModel() {
  
  private var _searchState = MutableLiveData<SearchState>()
  private var allWordsList: List<Word> = ArrayList()
  
  val searchState: LiveData<SearchState> get() = _searchState
  
  fun fetchAllWords() {
    allWordsList = database.wordDao().getAllWords()
    _searchState.value = DisplayingAllWords(allWordsList)
  }
  
  fun onSearchTextEntered(text: String) {
    if (text.isBlank()) {
      return
    }
    val formattedInput = "%$text%"
    val searchWords = database.wordDao().searchWords(formattedInput)
    if (searchWords.isEmpty()) {
      _searchState.value = NoWordsFound
    } else {
      _searchState.value = FoundWords(searchWords, text)
    }
  }
}