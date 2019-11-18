package com.arsvechkarev.info.presentation

import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.domain.model.WordEntity
import com.arsvechkarev.info.repository.InfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InfoViewModel @Inject constructor(
  private val repository: InfoRepository
) : BaseViewModel() {

  fun saveWord(wordEntity: WordEntity) {
    launch(Dispatchers.Main) {
      repository.saveWord(wordEntity)
    }
  }
  
  fun deleteWord(wordEntity: WordEntity) {
    launch(Dispatchers.Main) {
      repository.deleteWord(wordEntity)
    }
  }
  
}