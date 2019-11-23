package com.arsvechkarev.labels.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.inflate
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.list.Mode.Checkbox
import com.arsvechkarev.labels.list.Mode.Default
import com.arsvechkarev.labels.list.Mode.Simple

class LabelsAdapter(
  private val mode: Mode
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  
  private var data: List<Label> = ArrayList()
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (mode) {
      is Default -> DefaultLabelViewHolder(
        parent.inflate(R.layout.item_label_default),
        mode.labelCallback
      )
      is Simple -> SimpleLabelViewHolder(
        parent.inflate(R.layout.item_label_simple),
        mode.clickListener
      )
      is Checkbox -> CheckboxLabelViewHolder(
        parent.inflate(R.layout.item_label_checkbox),
        mode.labelCallback
      )
    }
  }
  
  override fun getItemCount() = data.size
  
  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (mode) {
      is Default -> (holder as DefaultLabelViewHolder).bind(data[position])
      is Simple -> (holder as SimpleLabelViewHolder).bind(data[position])
      is Checkbox -> (holder as CheckboxLabelViewHolder).bind(data[position])
    }
  }
  
  fun submitList(data: List<Label>) {
    this.data = data.sorted()
    notifyDataSetChanged()
  }
}