package com.arsvechkarev.list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.toWord
import com.arsvechkarev.storage.database.WordsDatabase
import javax.inject.Inject

class WordsListViewModel @Inject constructor(
  private val database: WordsDatabase
) : BaseViewModel() {
  
  fun fetchWords(): LiveData<List<Word>> {
    return Transformations.map(database.wordDao().getAllLive()) { list ->
      list.map { it.toWord() }
    }
  }
  
}