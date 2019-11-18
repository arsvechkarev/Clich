package com.arsvechkarev.info.presentation

import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.info.repository.InfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InfoViewModel @Inject constructor(
  private val repository: InfoRepository
) : BaseViewModel() {

  fun saveWord(word: Word) {
    launch(Dispatchers.Main) {
      repository.saveWord(word)
    }
  }
  
  fun deleteWord(word: Word) {
    launch(Dispatchers.Main) {
      repository.deleteWord(word)
    }
  }
  
}