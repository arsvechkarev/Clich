package com.arsvechkarev.labels.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.inflate
import com.arsvechkarev.labels.R
import kotlinx.android.synthetic.main.item_label.view.textLabel

class LabelsAdapter(
  private val clickListener: (Label) -> Unit = {}
) : RecyclerView.Adapter<LabelsAdapter.LabelViewHolder>() {
  
  private var data: List<Label> = ArrayList()
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelViewHolder {
    return LabelViewHolder(parent.inflate(R.layout.item_label))
  }
  
  override fun getItemCount() = data.size
  
  override fun onBindViewHolder(holder: LabelViewHolder, position: Int) {
    holder.bind(data[position], clickListener)
  }
  
  fun submitList(data: List<Label>) {
    this.data = data
    notifyDataSetChanged()
  }
  
  class LabelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Label, clickListener: (Label) -> Unit) {
      itemView.setOnClickListener { clickListener(item) }
      itemView.textLabel.text = item.name
    }
  }
}