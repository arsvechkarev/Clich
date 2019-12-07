package com.arsvechkarev.info.presentation

import androidx.lifecycle.LiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.WordsLabelsJoin
import com.arsvechkarev.storage.database.CentralDatabase
import javax.inject.Inject

class InfoViewModel @Inject constructor(
  private val database: CentralDatabase
) : BaseViewModel() {
  
  fun getLabelsForWord(word: Word): LiveData<List<Label>> {
    return database.wordsAndLabelsDao().getLabelsForWord(word.id!!)
  }
  
  fun updateWord(word: Word) {
    launchGlobal {
      database.wordDao().update(word)
    }
  }
  
  fun deleteWord(word: Word) {
    launchGlobal {
      database.wordDao().delete(word)
    }
  }
  
  fun saveWordWithLabels(word: Word, labels: MutableList<Label>) {
    launchGlobal {
      val id = database.wordDao().insert(word)
      labels.forEach {
        database.wordsAndLabelsDao().insert(WordsLabelsJoin(id, it.id!!))
      }
    }
  }
  
}