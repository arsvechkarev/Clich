package com.arsvechkarev.core

import com.arsvechkarev.core.domain.dao.WordsDao
import com.arsvechkarev.core.domain.model.Word
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListenableWordsDataSource @Inject constructor(
  private val wordsDao: WordsDao,
  private val dispatcherProvider: DispatcherProvider
) {
  
  private val listeners = ArrayList<WordsActionsListener>()
  
  fun addListener(listener: WordsActionsListener) {
    listeners.add(listener)
  }
  
  fun removeListener(listener: WordsActionsListener) {
    listeners.remove(listener)
  }
  
  suspend fun getAllWords(): List<Word> = withContext(dispatcherProvider.IO) {
    return@withContext wordsDao.getAllWords().applyAllWordsSorting()
  }
  
  suspend fun searchWords(input: String): List<Word> = withContext(dispatcherProvider.IO) {
    return@withContext wordsDao.searchWords(input)
  }
  
  suspend fun createWord(word: Word): Long = withContext(dispatcherProvider.IO) {
    val id = wordsDao.create(word)
    val newWord = word.copy(id = id)
    listeners.forEach { listener ->
      listener.onCreatedWord(newWord, createdFirstWord = wordsDao.getWordsCount() == 1)
    }
    return@withContext id
  }
  
  suspend fun updateWord(word: Word) = withContext(dispatcherProvider.IO) {
    wordsDao.update(word)
    listeners.forEach { it.onUpdatedWord(word) }
  }
  
  suspend fun deleteWord(word: Word) = withContext(dispatcherProvider.IO) {
    wordsDao.delete(word)
    listeners.forEach { listener ->
      listener.onDeletedWord(word, deletedLastWord = wordsDao.getWordsCount() == 0)
    }
  }
}