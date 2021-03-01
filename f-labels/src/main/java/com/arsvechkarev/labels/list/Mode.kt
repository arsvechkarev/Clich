package com.arsvechkarev.labels.list

import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.labels.list.viewholders.CheckboxLabelViewHolder
import com.arsvechkarev.core.CheckedChangedCallback
import com.arsvechkarev.labels.list.viewholders.DefaultLabelViewHolder
import com.arsvechkarev.labels.list.viewholders.SimpleLabelViewHolder

/**
 * Represents mode in which [LabelsAdapter] can work
 */
sealed class Mode {
  
  /** @see DefaultLabelViewHolder */
  class Default(val labelEditingCallback: LabelEditingCallback) : Mode()
  
  /** @see SimpleLabelViewHolder */
  class Simple(val clickListener: (Label) -> Unit) : Mode()
  
  /** @see CheckboxLabelViewHolder */
  class Checkbox(
    val alreadySelectedLabels: List<Label>,
    val callback: CheckedChangedCallback
  ) : Mode()
}