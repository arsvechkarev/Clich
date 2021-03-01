package com.arsvechkarev.labels.list.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.labels.list.Mode
import kotlinx.android.synthetic.main.item_label_simple.view.textLabel

class SimpleLabelViewHolder(
  itemView: View,
  private val mode: Mode.Simple
) : RecyclerView.ViewHolder(itemView) {
  
  fun bind(item: Label) {
    itemView.textLabel.text = item.name
    itemView.setOnClickListener { mode.clickListener(item) }
  }
}