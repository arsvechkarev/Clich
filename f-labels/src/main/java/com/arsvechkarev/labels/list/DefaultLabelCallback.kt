package com.arsvechkarev.labels.list

import com.arsvechkarev.core.domain.model.Label

interface DefaultLabelCallback {
  
  fun onStartEditing() {}
  fun onSaveLabel(label: Label, newName: String) {}
  fun onDeletingLabel(label: Label) {}
}