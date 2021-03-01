package com.arsvechkarev.labels.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.inflate
import com.arsvechkarev.core.recyler.DiffCallBack
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.list.Mode.Checkbox
import com.arsvechkarev.labels.list.Mode.Default
import com.arsvechkarev.labels.list.Mode.Simple
import com.arsvechkarev.labels.list.viewholders.CheckboxLabelViewHolder
import com.arsvechkarev.labels.list.viewholders.DefaultLabelViewHolder
import com.arsvechkarev.labels.list.viewholders.SimpleLabelViewHolder

/**
 * Adapter for displaying alreadySelectedLabels depending on [mode]
 */
class LabelsAdapter(private val mode: Mode) : ListAdapter<Label, ViewHolder>(DiffCallBack()) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (mode) {
    is Default -> DefaultLabelViewHolder(parent.inflate(R.layout.item_label_default), mode)
    is Simple -> SimpleLabelViewHolder(parent.inflate(R.layout.item_label_simple), mode)
    is Checkbox -> CheckboxLabelViewHolder(parent.inflate(R.layout.item_label_checkbox), mode)
  }
  
  override fun onBindViewHolder(holder: ViewHolder, position: Int) = when (mode) {
    is Default -> (holder as DefaultLabelViewHolder).bind(getItem(position))
    is Simple -> (holder as SimpleLabelViewHolder).bind(getItem(position))
    is Checkbox -> (holder as CheckboxLabelViewHolder).bind(getItem(position))
  }
}