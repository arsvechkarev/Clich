package com.arsvechkarev.labels.list

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.inflate
import com.arsvechkarev.labels.R

class LabelsAdapter(
  private val labelCallback: LabelCallback
) : RecyclerView.Adapter<DefaultLabelViewHolder>() {
  
  private var data: List<Label> = ArrayList()
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultLabelViewHolder {
    return DefaultLabelViewHolder(parent.inflate(R.layout.item_label_default), labelCallback)
  }
  
  override fun getItemCount() = data.size
  
  override fun onBindViewHolder(holder: DefaultLabelViewHolder, position: Int) {
    holder.bind(data[position])
  }
  
  fun submitList(data: List<Label>) {
    Log.d("zxcvb", "changed")
    this.data = data
    notifyDataSetChanged()
  }
}