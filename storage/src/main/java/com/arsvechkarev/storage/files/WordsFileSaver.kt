package com.arsvechkarev.storage.files

import com.arsvechkarev.core.Storage
import com.arsvechkarev.core.domain.model.Word

object WordsFileSaver {
  
  suspend fun getWords(storage: Storage): List<Word>? {
    return storage.get<MutableList<Word>>(FILENAME_ALL_WORDS)
  }
  
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