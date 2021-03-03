package com.arsvechkarev.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.DispatcherProvider
import com.arsvechkarev.core.ListenableWordsDataSource
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.search.presentation.SearchState.DisplayingAllWords
import com.arsvechkarev.search.presentation.SearchState.FoundWords
import com.arsvechkarev.search.presentation.SearchState.NoWordsFound
import java.util.Locale
import javax.inject.Inject

class SearchViewModel @Inject constructor(
  private val listenableWordsDataSource: ListenableWordsDataSource,
  dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {
  
  private var allWordsList: List<Word> = ArrayList()
  
  private var _searchState = MutableLiveData<SearchState>()
  val state: LiveData<SearchState> get() = _searchState
  
  fun fetchAllWords() {
    coroutine {
      allWordsList = listenableWordsDataSource.getAllWords()
      _searchState.value = DisplayingAllWords(allWordsList)
    }
  }
  
  fun onSearchTextEntered(text: String) {
    if (text.isBlank()) {
      _searchState.value = DisplayingAllWords(allWordsList)
      return
    }
    val lowercaseText = text.toLowerCase(Locale.getDefault())
    coroutine {
      val formattedInput = "%$lowercaseText%"
      val searchedWords = listenableWordsDataSource.searchWords(formattedInput)
      if (searchedWords.isEmpty()) {
        _searchState.value = NoWordsFound
      } else {
        sortResult(lowercaseText, searchedWords as MutableList<Word>)
        _searchState.value = FoundWords(searchedWords, lowercaseText)
      }
    }
  }
  
  private suspend fun sortResult(inputText: String, list: MutableList<Word>) =
    onIoThread {
      list.sortWith(Comparator { o1, o2 ->
        val occurrenceInFirstWord = o1.name.indexOf(inputText, ignoreCase = true)
        val occurrenceInSecondWord = o2.name.indexOf(inputText, ignoreCase = true)
        return@Comparator occurrenceInSecondWord - occurrenceInFirstWord
      })
    }
}