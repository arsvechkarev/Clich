package com.arsvechkarev.labels.list

import com.arsvechkarev.core.domain.model.Label

interface LabelEditingCallback {
  
  /**
   * Returns currently editing editing position, or -1 if no item is being edited
   */
  fun getCurrentlyEditingLabelPosition(): Int
  
  fun onStartEditing(position: Int) {}
  
  fun onEndEditing(position: Int) {}
  
  fun onUpdateLabel(label: Label, newName: String) {}
  
  fun onDeleteLabel(label: Label) {}
}