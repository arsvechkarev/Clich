package com.arsvechkarev.labels.list.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Label
import kotlinx.android.synthetic.main.item_label_checkbox.view.checkbox
import kotlinx.android.synthetic.main.item_label_checkbox.view.textLabel

class CheckboxNotCreatedWordViewHolder(
  itemView: View,
  private val callback: CheckedChangedCallback,
  private val labels: List<Label>
) : RecyclerView.ViewHolder(itemView) {
  
  fun bind(item: Label) {
    if (labels.contains(item)) {
      itemView.checkbox.isChecked = true
    }
    itemView.textLabel.text = item.name
    itemView.checkbox.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        callback.onLabelSelected(item)
      } else {
        callback.onLabelUnselected(item)
      }
    }
  }
}

interface CheckedChangedCallback {
  
  fun onLabelSelected(label: Label)
  
  fun onLabelUnselected(label: Label)
}