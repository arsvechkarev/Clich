package com.arsvechkarev.labels.presentation

import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.DispatcherProvider
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.domain.dao.LabelsDao
import com.arsvechkarev.core.domain.dao.WordsLabelsDao
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.map
import com.arsvechkarev.labels.presentation.LabelsState.ShowingLabels
import com.arsvechkarev.labels.presentation.LabelsState.ShowingNoLabels

@FeatureScope
class LabelsViewModel(
  private val labelsDao: LabelsDao,
  private val wordsLabelsDao: WordsLabelsDao,
  dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {
  
  var currentlyEditingPosition: Int = -1
  
  private var savedLabelsSize = 0
  
  fun fetchLabels() = labelsDao.getAllLive().map { labels ->
    val state = if (labels.isNotEmpty()) {
      ShowingLabels(labels, createOrDeleteHappened = savedLabelsSize != labels.size)
    } else {
      ShowingNoLabels
    }
    savedLabelsSize = labels.size
    return@map state
  }
  
  fun createLabel(labelName: String) {
    coroutine {
      labelsDao.create(Label(name = labelName))
    }
  }
  
  fun updateLabel(label: Label, newName: String) {
    coroutine {
      labelsDao.update(Label(label.id, newName))
    }
  }
  
  fun deleteLabel(label: Label) {
    coroutine {
      wordsLabelsDao.deleteFromLabel(label.id!!)
      labelsDao.delete(label)
    }
  }
}