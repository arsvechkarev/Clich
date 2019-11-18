package com.arsvechkarev.storage.files

import com.arsvechkarev.core.Storage
import com.arsvechkarev.core.domain.model.WordEntity

object WordsFileSaver {
  
  suspend fun getWords(storage: Storage): List<WordEntity>? {
    return storage.get<MutableList<WordEntity>>(FILENAME_ALL_WORDS)
  }
  
  suspend fun saveWord(storage: Storage, wordEntity: WordEntity) {
    var words = storage.get<MutableList<WordEntity>>(FILENAME_ALL_WORDS)
    if (words == null) words = ArrayList()
    words.add(wordEntity)
    storage.save(words, FILENAME_ALL_WORDS)
  }
  
  suspend fun deleteWord(storage: Storage, wordEntity: WordEntity) {
    val words = storage.get<MutableList<WordEntity>>(FILENAME_ALL_WORDS)
    words!!.remove(wordEntity)
    storage.save(words, FILENAME_ALL_WORDS)
  }
  
}