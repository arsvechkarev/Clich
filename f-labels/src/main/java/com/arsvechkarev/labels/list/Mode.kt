package com.arsvechkarev.labels.list

import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.labels.list.viewholders.CheckedChangedCallback

/**
 * Represents mode in which [LabelsAdapter] can work
 */
sealed class Mode {
  
  /** @see com.arsvechkarev.labels.list.viewholders.DefaultLabelViewHolder */
  class Default(val labelCallback: DefaultLabelCallback) : Mode()
  
  /** @see com.arsvechkarev.labels.list.viewholders.SimpleLabelViewHolder */
  class Simple(val clickListener: (Label) -> Unit) : Mode()
  
  /** @see com.arsvechkarev.labels.list.viewholders.CheckboxLabelViewHolder */
  class Checkbox(
    val word: Word,
    val alreadySelectedLabels: List<Label>
  ) : Mode()
  
  /** @see com.arsvechkarev.labels.list.viewholders.CheckboxNotCreatedWordViewHolder */
  class CheckboxNotCreatedWord(
    val callback: CheckedChangedCallback,
    val alreadySelectedLabels: List<Label>
  ) : Mode()
}