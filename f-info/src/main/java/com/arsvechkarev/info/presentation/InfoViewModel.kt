package com.arsvechkarev.info.presentation

import androidx.lifecycle.LiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.domain.model.LabelEntity
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.WordEntity
import com.arsvechkarev.storage.database.CentralDatabase
import javax.inject.Inject

class InfoViewModel @Inject constructor(
  private val database: CentralDatabase
) : BaseViewModel() {
  
  fun getLabels(word: Word): LiveData<List<LabelEntity>> {
    return database.wordsAndLabelsDao().getLabelsForWord(word.id!!)
  }
  
  fun saveWord(wordEntity: WordEntity) {
    launchGlobal {
      database.wordDao().create(wordEntity)
    }
  }
  
  fun updateWord(wordEntity: WordEntity) {
    launchGlobal {
      database.wordDao().update(wordEntity)
    }
  }
  
}