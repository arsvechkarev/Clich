package com.arsvechkarev.words.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.DispatcherProvider
import com.arsvechkarev.core.datasource.ListenableWordsDataSource
import com.arsvechkarev.core.datasource.WordsActionsListener
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.domain.dao.WordsLabelsDao
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.words.presentation.WordsListState.ShowingWords
import com.arsvechkarev.words.presentation.WordsListState.ShowingWordsForLabel

@FeatureScope
class WordsListViewModel(
  private val listenableWordsDataSource: ListenableWordsDataSource,
  private val wordsLabelsDao: WordsLabelsDao,
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
  
  private val _state = MutableLiveData<WordsListState>()
  val state: LiveData<WordsListState> get() = _state
  
  fun addWordsActionsListener(listener: WordsActionsListener) {
    this.actualListener = listener
    listenableWordsDataSource.addListener(proxyListener)
  }
  
  fun initialize(label: Label?) {
    if (label != null) {
      fetchWordsFor(label)
    } else {
      fetchAll()
    }
  }
  
  private fun fetchAll() {
    coroutine {
      val words = listenableWordsDataSource.getAllWords()
      _state.value = ShowingWords(words)
    }
  }
  
  private fun fetchWordsFor(label: Label) {
    coroutine {
      val words = onIoThread { wordsLabelsDao.getWordsForLabel(label.id!!) }
      _state.value = ShowingWordsForLabel(label, words)
    }
  }
  
  override fun onCleared() {
    super.onCleared()
    proxyListener.let(listenableWordsDataSource::removeListener)
    actualListener = null
  }
}
