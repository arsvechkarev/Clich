package com.arsvechkarev.info.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.inflate
import com.arsvechkarev.info.R
import kotlinx.android.synthetic.main.item_current_label.view.textLabel

class CurrentLabelsAdapter(
  private val clickListener: (Label) -> Unit = {}
) : RecyclerView.Adapter<CurrentLabelsAdapter.CurrentLabelsViewHolder>() {
  
  private var data: List<Label> = ArrayList()
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentLabelsViewHolder {
    return CurrentLabelsViewHolder(parent.inflate(R.layout.item_current_label))
  }
  
  override fun getItemCount() = data.size
  
  override fun onBindViewHolder(holder: CurrentLabelsViewHolder, position: Int) {
    holder.bind(data[position], clickListener)
  }
  
  fun submitList(data: List<Label>) {
    this.data = data
    notifyDataSetChanged()
  }
  
  class CurrentLabelsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Label, clickListener: (Label) -> Unit) {
      itemView.textLabel.text = item.name
      itemView.setOnClickListener { clickListener(item) }
    }
  }
}