package com.arsvechkarev.words.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.TimeDivider
import com.arsvechkarev.core.extensions.inflate
import com.arsvechkarev.core.recyler.AdapterDelegate
import com.arsvechkarev.core.recyler.DisplayableItem
import com.arsvechkarev.words.R
import kotlinx.android.synthetic.main.item_time_divider.view.textDate

class TimeDividerAdapterDelegate : AdapterDelegate {
  
  override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return TimeDividerViewHolder(parent.inflate(R.layout.item_time_divider))
  }
  
  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DisplayableItem) {
    (holder as TimeDividerViewHolder).bind(item as TimeDivider)
  }
  
  class TimeDividerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    
    fun bind(item: TimeDivider) {
      itemView.textDate.text = item.date
    }
  }
}