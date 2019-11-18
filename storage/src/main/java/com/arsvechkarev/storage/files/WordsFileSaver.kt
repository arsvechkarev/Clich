package com.arsvechkarev.storage.files

import com.arsvechkarev.core.Storage
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.WordEntity

object WordsFileSaver {
  
  suspend fun getWords(storage: Storage): List<Word>? {
    return storage.get<MutableList<Word>>(FILENAME_ALL_WORDS)
  }
  
  suspend fun saveWord(storage: Storage, wordEntity: Word) {
    var words = storage.get<MutableList<Word>>(FILENAME_ALL_WORDS)
    if (words == null) words = ArrayList()
    words.add(wordEntity)
    storage.save(words, FILENAME_ALL_WORDS)
  }
  
  suspend fun deleteWord(storage: Storage, wordEntity: Word) {
    val words = storage.get<MutableList<Word>>(FILENAME_ALL_WORDS)
    words!!.remove(wordEntity)
    storage.save(words, FILENAME_ALL_WORDS)
  }
  
}