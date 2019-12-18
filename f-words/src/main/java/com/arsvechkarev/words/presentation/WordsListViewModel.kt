package com.arsvechkarev.words.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.TimeDivider
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.recyler.DisplayableItem
import com.arsvechkarev.storage.CentralDatabase
import javax.inject.Inject

class WordsListViewModel @Inject constructor(
  private val database: CentralDatabase
) : BaseViewModel() {
  
  fun fetchWords(): LiveData<List<Word>> {
    return database.wordDao().getAll()
  }
  
  fun fetchAll(): LiveData<List<DisplayableItem>> {
    return database.wordDao().getAll().addDates()
  }
  
  fun getWordsOf(label: Label): LiveData<List<Word>> {
    return database.wordsAndLabelsDao().getWordsOfLabel(label.id!!)
  }
  
  private fun LiveData<List<Word>>.addDates(): LiveData<List<DisplayableItem>> {
    return Transformations.map(this) {
      val items = ArrayList<DisplayableItem>()
      for (i in it.indices) {
        if (i != it.size - 1) {
          items.add(it[i])
          if (it[i].creationDate < it[i + 1].creationDate) {
            items.add(TimeDivider.of(it[i].creationDate))
          }
        } else {
          items.add(it[i])
          items.add(TimeDivider.of(it[i].creationDate))
        }
      }
      return@map items
    }
  }
  
}
