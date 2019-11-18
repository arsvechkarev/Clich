package com.arsvechkarev.info.repository

import com.arsvechkarev.core.Storage
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.storage.files.WordsFileSaver
import javax.inject.Inject

class InfoRepository @Inject constructor(
  private val storage: Storage
) {
  
  suspend fun saveWord(word: Word) {
    WordsFileSaver.saveWord(storage, word)
  }
  
  suspend fun deleteWord(word: Word) {
    WordsFileSaver.deleteWord(storage, word)
  }
  
}