package com.arsvechkarev.clich.presentation

import androidx.lifecycle.LiveData
import com.arsvechkarev.core.BaseViewModel
import com.arsvechkarev.core.extensions.map
import com.arsvechkarev.storage.CentralDatabase
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val database: CentralDatabase
) : BaseViewModel() {
  
  fun loadLabels(): LiveData<MainScreenState> {
    return database.labelsDao().getAll().map { labels ->
      if (labels.isEmpty()) {
        MainScreenState.NoLabels
      } else {
        MainScreenState.LoadedLabels(labels)
      }
    }
  }
}