package com.arsvechkarev.info.presentation

import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.model.Word
import com.arsvechkarev.info.repository.InfoRepository
import javax.inject.Inject

class InfoViewModel @Inject constructor(
  private val repository: InfoRepository
) : BaseViewModel() {

  fun saveWord(word: Word) {
    launchCoroutine {
      repository.saveWord(word)
    }
  }
  
  fun deleteWord(word: Word) {
    launchCoroutine {
      repository.deleteWord(word)
    }
  }
  
}