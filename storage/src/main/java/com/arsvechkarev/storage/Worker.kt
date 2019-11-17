package com.arsvechkarev.storage

import com.arsvechkarev.core.Storage
import com.arsvechkarev.core.model.Word

object Worker {
  
  suspend fun saveWord(storage: Storage, word: Word) {
    var words = storage.get<MutableList<Word>>(FILENAME_ALL_WORDS)
    if (words == null) words = ArrayList()
    words.add(word)
    storage.save(words, FILENAME_ALL_WORDS)
  }
  
  suspend fun deleteWord(storage: Storage, word: Word) {
    val words = storage.get<MutableList<Word>>(FILENAME_ALL_WORDS)
    words!!.remove(word)
    storage.save(words, FILENAME_ALL_WORDS)
  }
  
}