package com.arsvechkarev.core

import com.arsvechkarev.core.domain.model.Word

interface WordsActionsListener {
  
  fun onCreatedWord(word: Word, createdFirstWord: Boolean)
  
  fun onUpdatedWord(word: Word)
  
  fun onDeletedWord(word: Word, deletedLastWord: Boolean)
}