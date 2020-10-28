package com.arsvechkarev.labels.list

import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.labels.list.viewholders.CheckboxLabelViewHolder
import com.arsvechkarev.labels.list.viewholders.CheckboxNotCreatedWordViewHolder
import com.arsvechkarev.labels.list.viewholders.CheckedChangedCallback
import com.arsvechkarev.labels.list.viewholders.DefaultLabelViewHolder
import com.arsvechkarev.labels.list.viewholders.SimpleLabelViewHolder

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
    val alreadySelectedLabels: List<Label>
  ) : Mode()
  
  /** @see CheckboxNotCreatedWordViewHolder */
  class CheckboxNotCreatedWord(
    val callback: CheckedChangedCallback,
    val alreadySelectedLabels: List<Label>
  ) : Mode()
}