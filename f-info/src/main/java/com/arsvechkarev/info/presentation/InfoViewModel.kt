package com.arsvechkarev.info.presentation

import androidx.lifecycle.LiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.storage.database.CentralDatabase
import javax.inject.Inject

class InfoViewModel @Inject constructor(
  private val database: CentralDatabase
) : BaseViewModel() {
  
  fun getLabels(word: Word): LiveData<List<Label>> {
    return database.wordsAndLabelsDao().getLabelsForWord(word.id!!)
  }
  
  fun saveWord(word: Word) {
    launchGlobal {
      database.wordDao().create(word)
    }
  }
  
  fun updateWord(word: Word) {
    launchGlobal {
      database.wordDao().update(word)
    }
  }
  
}