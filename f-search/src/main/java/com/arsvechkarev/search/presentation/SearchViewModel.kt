package com.arsvechkarev.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.storage.CentralDatabase
import javax.inject.Inject

class SearchViewModel @Inject constructor(
  private val database: CentralDatabase
) : BaseViewModel() {
  
  fun searchWords(input: String): LiveData<List<Word>> {
    val formattedInput = "%$input%"
    return database.wordDao().search(formattedInput)
  }
  
  fun getAllWords(): LiveData<List<Word>> {
    return database.wordDao().fetchAll()
  }
}