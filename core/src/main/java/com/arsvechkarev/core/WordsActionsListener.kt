package com.arsvechkarev.core

import com.arsvechkarev.core.domain.model.Word

interface WordsActionsListener {
  
  fun onCreated(word: Word, createdFirstWord: Boolean)
  
  fun onUpdated(word: Word)
  
  fun onDeleted(word: Word, deletedLastWord: Boolean)
}