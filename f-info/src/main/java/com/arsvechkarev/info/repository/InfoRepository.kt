package com.arsvechkarev.info.repository

import com.arsvechkarev.core.Storage
import com.arsvechkarev.core.domain.model.WordEntity
import com.arsvechkarev.storage.files.WordsFileSaver
import javax.inject.Inject

class InfoRepository @Inject constructor(
  private val storage: Storage
) {
  
  suspend fun saveWord(wordEntity: WordEntity) {
    WordsFileSaver.saveWord(storage, wordEntity)
  }
  
  suspend fun deleteWord(wordEntity: WordEntity) {
    WordsFileSaver.deleteWord(storage, wordEntity)
  }
  
}