package com.arsvechkarev.info.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.LabelEntity
import com.arsvechkarev.core.extensions.inflate
import com.arsvechkarev.info.R

class CurrentLabelsAdapter(
  private val clickListener: (LabelEntity) -> Unit = {}
) : RecyclerView.Adapter<CurrentLabelsAdapter.CurrentLabelsViewHolder>() {
  
  private var data: List<LabelEntity> = ArrayList()
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentLabelsViewHolder {
    return CurrentLabelsViewHolder(parent.inflate(R.layout.item_current_label))
  }
  
  override fun getItemCount() = data.size
  
  override fun onBindViewHolder(holder: CurrentLabelsViewHolder, position: Int) {
    holder.bind(data[position], clickListener)
  }
  
  fun submitList(data: List<LabelEntity>) {
    this.data = data
    notifyDataSetChanged()
  }
  
  class CurrentLabelsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: LabelEntity, clickListener: (LabelEntity) -> Unit) {
      itemView.setOnClickListener { clickListener(item) }
    }
  }
}