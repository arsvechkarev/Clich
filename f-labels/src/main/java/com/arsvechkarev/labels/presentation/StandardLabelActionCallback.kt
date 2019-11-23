package com.arsvechkarev.labels.presentation

import android.app.Activity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.inBackground
import com.arsvechkarev.core.extensions.showKeyboard
import com.arsvechkarev.labels.list.DefaultLabelViewHolder
import com.arsvechkarev.labels.list.LabelCallback
import com.arsvechkarev.storage.database.CentralDatabase

class StandardLabelActionCallback(
  private val activity: Activity,
  private val layoutManager: LinearLayoutManager,
  private val recyclerLabels: RecyclerView
) : LabelCallback {
  
  override fun onStartEditing() {
    showKeyboard(activity)
    val first = layoutManager.findFirstVisibleItemPosition()
    val last = layoutManager.findFirstVisibleItemPosition()
    for (position in first until last) {
      val viewHolder =
        recyclerLabels.findViewHolderForAdapterPosition(position) as DefaultLabelViewHolder
      viewHolder.endEditingMode()
    }
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