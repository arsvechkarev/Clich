package com.arsvechkarev.labels.list

import com.arsvechkarev.core.domain.model.Label

sealed class Mode {
  
  class Default(val labelCallback: DefaultLabelCallback) : Mode()
  
  class Simple(val clickListener: (Label) -> Unit) : Mode()
  
  class Checkbox(val labelCallback: CheckboxLabelCallback) : Mode()
}