package com.arsvechkarev.labels.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.DispatcherProvider
import com.arsvechkarev.core.domain.dao.LabelsDao
import com.arsvechkarev.core.domain.dao.WordsLabelsDao
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.WordsLabelsJoin
import com.arsvechkarev.labels.presentation.LabelsCheckboxState.ShowingLabels
import com.arsvechkarev.labels.presentation.LabelsCheckboxState.ShowingNoLabels

class LabelsCheckboxViewModel(
  private val word: Word,
  private val labelsDao: LabelsDao,
  private val wordsLabelsDao: WordsLabelsDao,
  dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {
  
  private val _state = MutableLiveData<LabelsCheckboxState>()
  val state: LiveData<LabelsCheckboxState> get() = _state
  
  private val checkedLabels = HashSet<Label>()
  
  fun fetchLabelsForWord() {
    coroutine {
      val labelsForWord = onIoThread { wordsLabelsDao.getLabelsForWord(word.id!!) }
      checkedLabels.addAll(labelsForWord)
      val allLabels = labelsDao.getAll()
      if (allLabels.isNotEmpty()) {
        _state.value = ShowingLabels(allLabels)
      } else {
        _state.value = ShowingNoLabels
      }
    }
  }
  
  fun onLabelChecked(label: Label) {
    checkedLabels.add(label)
    coroutine {
      onIoThread { wordsLabelsDao.create(WordsLabelsJoin(word.id!!, label.id!!)) }
    }
  }
  
  fun onLabelUnchecked(label: Label) {
    checkedLabels.remove(label)
    coroutine {
      onIoThread { wordsLabelsDao.delete(WordsLabelsJoin(word.id!!, label.id!!)) }
    }
  }
  
  fun isLabelChecked(label: Label): Boolean {
    return checkedLabels.contains(label)
  }
}