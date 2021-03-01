package com.arsvechkarev.words.presentation

import androidx.lifecycle.LiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.CentralDatabase
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word

@FeatureScope
class WordsListViewModel(
  private val database: CentralDatabase
) : BaseViewModel() {
  
  fun fetchAll(): LiveData<List<Word>> {
    return database.wordDao().getWordsLiveData()
  }
  
  fun getWordsFor(label: Label): LiveData<List<Word>> {
    return database.wordsAndLabelsDao().getWordsForLabel(label.id!!)
  }
}
