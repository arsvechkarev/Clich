package com.arsvechkarev.words.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.DispatcherProvider
import com.arsvechkarev.core.ListenableWordsDataSource
import com.arsvechkarev.core.WordsActionsListener
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.domain.model.Word

@FeatureScope
class WordsListViewModel(
  private val listenableWordsDataSource: ListenableWordsDataSource,
  dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {
  
  private var actualListener: WordsActionsListener? = null
  
  private val proxyListener = object : WordsActionsListener {
    
    override fun onCreatedWord(word: Word, createdFirstWord: Boolean) {
      coroutine { actualListener?.onCreatedWord(word, createdFirstWord) }
    }
    
    override fun onUpdatedWord(word: Word) {
      coroutine { actualListener?.onUpdatedWord(word) }
    }
    
    override fun onDeletedWord(word: Word, deletedLastWord: Boolean) {
      coroutine { actualListener?.onDeletedWord(word, deletedLastWord) }
    }
  }
  
  private val _state = MutableLiveData<List<Word>>()
  val state: LiveData<List<Word>> get() = _state
  
  fun addWordsActionsListener(listener: WordsActionsListener) {
    this.actualListener = listener
    listenableWordsDataSource.addListener(proxyListener)
  }
  
  fun fetchAll() {
    coroutine {
      val allWords = listenableWordsDataSource.getAllWords()
      println("aaaaa: all words $allWords")
      _state.value = allWords
    }
  }
  
  override fun onCleared() {
    super.onCleared()
    proxyListener.let(listenableWordsDataSource::removeListener)
    actualListener = null
  }
}
