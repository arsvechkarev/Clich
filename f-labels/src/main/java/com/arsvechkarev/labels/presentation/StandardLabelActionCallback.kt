package com.arsvechkarev.labels.presentation

import android.app.Activity
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.inBackground
import com.arsvechkarev.core.extensions.showKeyboard
import com.arsvechkarev.labels.list.DefaultLabelCallback
import com.arsvechkarev.storage.database.CentralDatabase

class StandardLabelActionCallback(
  private val activity: Activity
) : DefaultLabelCallback {
  
  override fun onStartEditing() {
    showKeyboard(activity)
  }
  
  override fun onSaveLabel(label: Label, newName: String) {
    label.name = newName
    inBackground {
      CentralDatabase.instance.labelsDao().update(label)
    }
  }
  
  override fun onDeletingLabel(label: Label) {
    inBackground {
      CentralDatabase.instance.labelsDao().delete(label)
    }
  }
}