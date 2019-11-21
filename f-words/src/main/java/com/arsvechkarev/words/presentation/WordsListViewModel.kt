package com.arsvechkarev.words.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.toWord
import com.arsvechkarev.storage.database.CentralDatabase
import javax.inject.Inject

class WordsListViewModel @Inject constructor(
  private val database: CentralDatabase
) : BaseViewModel() {
  
  fun fetchWords(): LiveData<List<Word>> {
    return Transformations.map(database.wordDao().getAllLive()) { list ->
      list.map { it.toWord() }
    }
  }
  
}