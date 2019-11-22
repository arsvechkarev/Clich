package com.arsvechkarev.words.presentation

import androidx.lifecycle.LiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.storage.database.CentralDatabase
import javax.inject.Inject

class WordsListViewModel @Inject constructor(
  private val database: CentralDatabase
) : BaseViewModel() {
  
  fun fetchWords(): LiveData<List<Word>> {
    return database.wordDao().getAll()
  }
  
}