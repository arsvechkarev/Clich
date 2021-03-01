package com.arsvechkarev.words.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.CentralDatabase
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.TimeDivider
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.recyler.DisplayableItem

@FeatureScope
class WordsListViewModel(private val database: CentralDatabase) : BaseViewModel() {
  
  fun fetchAll(): LiveData<List<Word>> {
    return database.wordDao().fetchAll()
  }
  
  fun getWordsFor(label: Label): LiveData<List<Word>> {
    return database.wordsAndLabelsDao().getWordsForLabel(label.id!!)
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
