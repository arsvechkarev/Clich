package com.arsvechkarev.labels.list

sealed class Mode {
  
  class Default(val labelCallback: DefaultLabelCallback) : Mode()
  
  class Simple : Mode()
  
  class Checkbox(val labelCallback: CheckboxLabelCallback) : Mode()
}