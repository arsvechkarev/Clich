package com.arsvechkarev.info.repository

import com.arsvechkarev.core.Storage
import com.arsvechkarev.core.model.Word
import com.arsvechkarev.storage.Worker
import javax.inject.Inject

class InfoRepository @Inject constructor(
  private val storage: Storage
) {
  
  suspend fun saveWord(word: Word) {
    Worker.saveWord(storage, word)
  }
  
  suspend fun deleteWord(word: Word) {
    Worker.deleteWord(storage, word)
  }
  
}