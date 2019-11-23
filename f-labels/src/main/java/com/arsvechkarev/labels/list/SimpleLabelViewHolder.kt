package com.arsvechkarev.labels.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Label
import kotlinx.android.synthetic.main.item_label_simple.view.textLabel

class SimpleLabelViewHolder(
  itemView: View,
  private val clickListener: (Label) -> Unit
) : RecyclerView.ViewHolder(itemView) {
  
  fun bind(item: Label) {
    itemView.textLabel.text = item.name
    itemView.setOnClickListener { clickListener(item) }
  }
}