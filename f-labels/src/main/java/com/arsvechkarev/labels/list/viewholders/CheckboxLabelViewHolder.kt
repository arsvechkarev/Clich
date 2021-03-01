package com.arsvechkarev.labels.list.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.labels.list.Mode
import kotlinx.android.synthetic.main.item_label_checkbox.view.checkbox
import kotlinx.android.synthetic.main.item_label_checkbox.view.textLabel

class CheckboxLabelViewHolder(
  itemView: View,
  private val mode: Mode.Checkbox,
) : RecyclerView.ViewHolder(itemView) {
  
  fun bind(item: Label) {
    if (mode.alreadySelectedLabels.contains(item)) {
      itemView.checkbox.isChecked = true
    }
    itemView.textLabel.text = item.name
    itemView.checkbox.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        mode.callback.onLabelSelected(item)
      } else {
        mode.callback.onLabelUnselected(item)
      }
    }
  }
}