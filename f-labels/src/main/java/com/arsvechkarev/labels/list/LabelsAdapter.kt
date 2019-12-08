package com.arsvechkarev.labels.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.DisplayableItem.DiffCallBack
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.inflate
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.list.Mode.Checkbox
import com.arsvechkarev.labels.list.Mode.CheckboxNotCreatedWord
import com.arsvechkarev.labels.list.Mode.Default
import com.arsvechkarev.labels.list.Mode.Simple
import com.arsvechkarev.labels.list.viewholders.CheckboxLabelViewHolder
import com.arsvechkarev.labels.list.viewholders.CheckboxNotCreatedWordViewHolder
import com.arsvechkarev.labels.list.viewholders.DefaultLabelViewHolder
import com.arsvechkarev.labels.list.viewholders.SimpleLabelViewHolder

/**
 * Adapter for displaying alreadySelectedLabels depending on [mode]
 */
class LabelsAdapter(
  private val mode: Mode
) : ListAdapter<Label, RecyclerView.ViewHolder>(DiffCallBack()) {
  
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
        parent.inflate(R.layout.item_label_checkbox), mode.word, mode.alreadySelectedLabels
      )
      is CheckboxNotCreatedWord -> CheckboxNotCreatedWordViewHolder(
        parent.inflate(R.layout.item_label_checkbox), mode.callback, mode.alreadySelectedLabels
      )
    }
  }
  
  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (mode) {
      is Default -> (holder as DefaultLabelViewHolder).bind(getItem(position))
      is Simple -> (holder as SimpleLabelViewHolder).bind(getItem(position))
      is Checkbox -> (holder as CheckboxLabelViewHolder).bind(getItem(position))
      is CheckboxNotCreatedWord -> (holder as CheckboxNotCreatedWordViewHolder).bind(getItem(position))
    }
  }
  
  override fun submitList(data: List<Label>?) {
    super.submitList(data?.sorted())
    notifyDataSetChanged()
  }
}