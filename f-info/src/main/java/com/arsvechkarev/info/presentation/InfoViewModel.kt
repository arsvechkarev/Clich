package com.arsvechkarev.info.presentation

import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.domain.model.WordEntity
import com.arsvechkarev.storage.database.CentralDatabase
import javax.inject.Inject

class InfoViewModel @Inject constructor(
  private val database: CentralDatabase
) : BaseViewModel() {
  
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