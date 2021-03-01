package com.arsvechkarev.info.presentation

import androidx.lifecycle.LiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.CentralDatabase
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.WordsLabelsJoin
import javax.inject.Inject

class InfoViewModel @Inject constructor(
  private val database: CentralDatabase
) : BaseViewModel() {
  
  fun getLabelsForWord(word: Word): LiveData<List<Label>> {
    return database.wordsAndLabelsDao().getLabelsForWord(word.id!!)
  }
  
  fun updateWord(word: Word) {
    coroutine { database.wordDao().update(word) }
  }
  
  fun deleteWord(word: Word) {
    coroutine {
      database.wordsAndLabelsDao().deleteFromWord(word.id!!)
      database.wordDao().delete(word)
    }
  }
  
  fun saveWordWithLabels(word: Word, labels: MutableList<Label>) {
    coroutine {
      val id = database.wordDao().create(word)
      labels.forEach {
        database.wordsAndLabelsDao().create(WordsLabelsJoin(id, it.id!!))
      }
    }
  }
}