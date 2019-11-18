package com.arsvechkarev.list.repository

import com.arsvechkarev.core.Storage
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.WordsDatabase
import javax.inject.Inject

class WordsListRepository @Inject constructor(
  private val storage: Storage
) {
  
  suspend fun fetchWords(): List<Word>? {
    return WordsDatabase.getInstance().wordDao().getAll()
  }
}