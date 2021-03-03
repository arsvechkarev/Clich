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
    itemView.checkbox.isChecked = mode.callback.isLabelChecked(item)
    itemView.textLabel.text = item.name
    itemView.checkbox.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        mode.callback.onLabelChecked(item)
      } else {
        mode.callback.onLabelUnchecked(item)
      }
    }
  }
}