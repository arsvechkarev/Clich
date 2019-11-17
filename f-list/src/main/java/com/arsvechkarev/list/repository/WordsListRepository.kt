package com.arsvechkarev.list.repository

import com.arsvechkarev.core.Storage
import com.arsvechkarev.core.model.Word
import com.arsvechkarev.storage.FILENAME_ALL_WORDS
import javax.inject.Inject

class WordsListRepository @Inject constructor(
  private val storage: Storage
) {
  
  suspend fun fetchWords(): List<Word>? {
    return storage.get(FILENAME_ALL_WORDS)
  }
}