package com.arsvechkarev.labels.list

import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.labels.list.viewholders.CheckboxLabelViewHolder
import com.arsvechkarev.labels.list.viewholders.DefaultLabelViewHolder
import com.arsvechkarev.labels.list.viewholders.SimpleLabelViewHolder
import com.arsvechkarev.labels.presentation.LabelCheckedCallback

/**
 * Represents mode in which [LabelsAdapter] can work
 */
sealed class Mode {
  
  /** @see DefaultLabelViewHolder */
  class Default(val labelEditingCallback: LabelEditingCallback) : Mode()
  
  /** @see SimpleLabelViewHolder */
  class Simple(val clickListener: (Label) -> Unit) : Mode()
  
  /** @see CheckboxLabelViewHolder */
  class Checkbox(val callback: LabelCheckedCallback) : Mode()
}