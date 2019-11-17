package com.arsvechkarev.list.presentation

import androidx.lifecycle.MutableLiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.list.repository.WordsListRepository
import javax.inject.Inject

class WordsListViewModel @Inject constructor(
  private val repository: WordsListRepository
) : BaseViewModel() {

  val state = MutableLiveData<State>()
  
  fun fetchWords() {
    launchCoroutine {
      val words = repository.fetchWords()
      if (words.isNullOrEmpty()) {
        state.value = State.Empty
      } else {
        state.value = State.Success(words)
      }
    }
  }
  
}