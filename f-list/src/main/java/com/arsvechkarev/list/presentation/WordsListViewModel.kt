package com.arsvechkarev.list.presentation

import androidx.lifecycle.LiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.domain.model.WordEntity
import com.arsvechkarev.storage.database.WordsDatabase
import javax.inject.Inject

class WordsListViewModel @Inject constructor(
  private val database: WordsDatabase
) : BaseViewModel() {
  
  fun fetchWords(): LiveData<List<WordEntity>> {
    return database.wordDao().getAllLive()
  }
  
}