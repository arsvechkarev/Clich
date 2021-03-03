package com.arsvechkarev.words.presentation

import androidx.lifecycle.LiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.DispatcherProvider
import com.arsvechkarev.core.applyAllWordsSorting
import com.arsvechkarev.core.domain.dao.WordsLabelsDao
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.map

class WordsForLabelViewModel(
  private val wordsLabelsDao: WordsLabelsDao,
  dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {
  
  fun fetchWordsForLabelLive(label: Label): LiveData<List<Word>> {
    return wordsLabelsDao.getWordsForLabelLive(label.id!!).map(List<Word>::applyAllWordsSorting)
  }
}