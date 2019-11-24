package com.arsvechkarev.labels.list

import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word

sealed class Mode {
  
  class Default(val labelCallback: DefaultLabelCallback) : Mode()
  
  class Simple(val clickListener: (Label) -> Unit) : Mode()
  
  class Checkbox(
    val word: Word
  ) : Mode()
}