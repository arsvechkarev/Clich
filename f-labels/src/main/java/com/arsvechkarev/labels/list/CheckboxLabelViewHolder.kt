package com.arsvechkarev.labels.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Label
import kotlinx.android.synthetic.main.item_label_checkbox.view.checkbox
import kotlinx.android.synthetic.main.item_label_checkbox.view.textLabel

class CheckboxLabelViewHolder(
  itemView: View,
  private val labelCallback: CheckboxLabelCallback
) : RecyclerView.ViewHolder(itemView) {
  
  fun bind(item: Label) {
    itemView.textLabel.text = item.name
    itemView.checkbox.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        labelCallback.onCheck(item)
      } else {
        labelCallback.onUncheck(item)
      }
    }
  }
}