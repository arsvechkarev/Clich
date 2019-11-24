package com.arsvechkarev.labels.list

import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word

/**
 * Represents mode in which [LabelsAdapter] can work
 */
sealed class Mode {
  
  /** @see DefaultLabelViewHolder */
  class Default(val labelCallback: DefaultLabelCallback) : Mode()
  
  /** @see SimpleLabelViewHolder */
  class Simple(val clickListener: (Label) -> Unit) : Mode()
  
  /** @see CheckboxLabelViewHolder */
  class Checkbox(
    val word: Word,
    val labels: List<Label>
  ) : Mode()
}