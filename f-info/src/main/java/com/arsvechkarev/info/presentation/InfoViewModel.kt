package com.arsvechkarev.info.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.datasource.ListenableWordsDataSource
import com.arsvechkarev.core.domain.dao.WordsLabelsDao
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.WordsLabelsJoin
import com.arsvechkarev.info.presentation.InfoState.ExistingWordState
import com.arsvechkarev.info.presentation.InfoState.NewWordState
import org.threeten.bp.LocalDate
import javax.inject.Inject

class InfoViewModel @Inject constructor(
  private val listenableWordsDataSource: ListenableWordsDataSource,
  private val wordsLabelsDao: WordsLabelsDao
) : BaseViewModel() {
  
  private var previousWord: Word? = null
  private var currentLabels = ArrayList<Label>()
  
  private val _state = MutableLiveData<InfoState>()
  val state: LiveData<InfoState> get() = _state
  
  fun initialize(word: Word?) {
    previousWord = word
    if (word != null) {
      _state.value = ExistingWordState(word)
    } else {
      _state.value = NewWordState
    }
  }
  
  fun fetchLabelsForWord(): LiveData<List<Label>>? {
    previousWord ?: return null
    return wordsLabelsDao.getLabelsForWordLive(previousWord!!.id!!)
  }
  
  fun addLabel(label: Label) {
    currentLabels.add(label)
    coroutine {
      val wordsLabelsJoin = WordsLabelsJoin(previousWord!!.id!!, label.id!!)
      wordsLabelsDao.create(wordsLabelsJoin)
    }
  }
  
  fun deleteLabel(label: Label) {
    currentLabels.remove(label)
    coroutine {
      val wordsLabelsJoin = WordsLabelsJoin(previousWord!!.id!!, label.id!!)
      wordsLabelsDao.delete(wordsLabelsJoin)
    }
  }
  
  fun onAddLabelsClicked() {
    _state.value = InfoState.OnAddLabelsClicked(previousWord)
  }
  
  fun saveData(
    name: String,
    definition: String,
    examples: String
  ) {
    if (name.isBlank()) {
      return
    }
    val word = Word(
      id = previousWord?.id,
      name = name,
      definition = definition,
      examples = examples,
      creationDate = previousWord?.creationDate ?: LocalDate.now().toEpochDay()
    )
    if (previousWord != null) {
      updateWord(word)
    } else {
      saveWordWithLabels(word)
    }
  }
  
  fun deleteWord() {
    coroutine {
      wordsLabelsDao.deleteFromWord(previousWord!!.id!!)
      listenableWordsDataSource.deleteWord(previousWord!!)
    }
  }
  
  private fun updateWord(word: Word) {
    coroutine { listenableWordsDataSource.updateWord(word) }
  }
  
  private fun saveWordWithLabels(word: Word) {
    coroutine {
      val id = listenableWordsDataSource.createWord(word)
      currentLabels.forEach { label ->
        wordsLabelsDao.create(WordsLabelsJoin(id, label.id!!))
      }
    }
  }
}