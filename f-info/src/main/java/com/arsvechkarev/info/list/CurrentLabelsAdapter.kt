package com.arsvechkarev.info.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.DisplayableItem.DiffCallBack
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.inflate
import com.arsvechkarev.info.R
import com.arsvechkarev.info.list.CurrentLabelsAdapter.CurrentLabelsViewHolder
import kotlinx.android.synthetic.main.item_current_label.view.textLabel

class CurrentLabelsAdapter(
  private val clickListener: (Label) -> Unit = {}
) : ListAdapter<Label, CurrentLabelsViewHolder>(DiffCallBack()) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentLabelsViewHolder {
    return CurrentLabelsViewHolder(parent.inflate(R.layout.item_current_label))
  }
  
  override fun onBindViewHolder(holder: CurrentLabelsViewHolder, position: Int) {
    holder.bind(getItem(position), clickListener)
  }
  
  class CurrentLabelsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Label, clickListener: (Label) -> Unit) {
      itemView.textLabel.text = item.name
      itemView.setOnClickListener { clickListener(item) }
    }
  }
}