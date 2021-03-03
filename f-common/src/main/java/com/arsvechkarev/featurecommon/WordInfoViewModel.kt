package com.arsvechkarev.featurecommon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.DispatcherProvider
import com.arsvechkarev.core.ListenableWordsDataSource
import com.arsvechkarev.core.domain.dao.LabelsDao
import com.arsvechkarev.core.domain.dao.WordsLabelsDao
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.WordsLabelsJoin
import com.arsvechkarev.core.extensions.add
import com.arsvechkarev.core.extensions.addAll
import com.arsvechkarev.core.extensions.remove
import com.arsvechkarev.featurecommon.LabelsWithCheckboxState.ShowingLabelsWithCheckbox
import com.arsvechkarev.featurecommon.LabelsWithCheckboxState.ShowingNoLabelsWithCheckbox
import java.util.Date
import java.util.HashSet

class WordInfoViewModel(
  private val listenableWordsDataSource: ListenableWordsDataSource,
  private val labelsDao: LabelsDao,
  private val wordsLabelsDao: WordsLabelsDao,
  dispatcherProvider: DispatcherProvider,
) : BaseViewModel(dispatcherProvider) {
  
  var alreadyExistingWord: Word? = null
  
  private val labelsForWordLiveData = MutableLiveData<HashSet<Label>>().apply { value = HashSet() }
  val labelsForWord: LiveData<HashSet<Label>> get() = labelsForWordLiveData
  
  private val _labelsState = MutableLiveData<LabelsWithCheckboxState>()
  val labelsState: LiveData<LabelsWithCheckboxState> get() = _labelsState
  
  fun fetchAllLabels() {
    coroutine {
      val labels = onIoThread { labelsDao.getAll() }
      if (labels.isNotEmpty()) {
        _labelsState.value = ShowingLabelsWithCheckbox(labels)
      } else {
        _labelsState.value = ShowingNoLabelsWithCheckbox
      }
    }
  }
  
  fun fetchLabelsForWordIfAny() {
    alreadyExistingWord ?: return
    coroutine {
      val collection = onIoThread { wordsLabelsDao.getLabelsForWord(alreadyExistingWord!!.id!!) }
      labelsForWordLiveData.addAll(collection)
    }
  }
  
  fun addLabel(label: Label) {
    labelsForWordLiveData.add(label)
    val word = alreadyExistingWord
    if (word != null) {
      coroutine {
        onIoThread { wordsLabelsDao.create(WordsLabelsJoin(word.id!!, label.id!!)) }
      }
    }
  }
  
  fun removeLabel(label: Label) {
    labelsForWordLiveData.remove(label)
    val word = alreadyExistingWord
    if (word != null) {
      coroutine {
        onIoThread { wordsLabelsDao.delete(WordsLabelsJoin(word.id!!, label.id!!)) }
      }
    }
  }
  
  fun isLabelChecked(label: Label): Boolean {
    return labelsForWordLiveData.value!!.contains(label)
  }
  
  fun saveData(
    name: String,
    definition: String,
    examples: String
  ) {
    if (name.isBlank()) {
      return
    }
    val word = Word(
      id = alreadyExistingWord?.id,
      name = name,
      definition = definition,
      examples = examples,
      creationDate = alreadyExistingWord?.creationDate ?: Date().time
    )
    if (alreadyExistingWord != null) {
      updateWord(word)
    } else {
      saveWordWithLabels(word)
    }
  }
  
  fun deleteWord() {
    coroutine {
      val word = alreadyExistingWord!!
      wordsLabelsDao.deleteFromWord(word.id!!)
      listenableWordsDataSource.deleteWord(word)
    }
  }
  
  private fun updateWord(word: Word) {
    coroutine { listenableWordsDataSource.updateWord(word) }
  }
  
  private fun saveWordWithLabels(word: Word) {
    coroutine {
      val id = listenableWordsDataSource.createWord(word)
      labelsForWordLiveData.value!!.forEach { label ->
        wordsLabelsDao.create(WordsLabelsJoin(id, label.id!!))
      }
    }
  }
}