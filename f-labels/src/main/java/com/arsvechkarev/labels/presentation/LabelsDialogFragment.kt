package com.arsvechkarev.labels.presentation

import androidx.fragment.app.DialogFragment
import com.arsvechkarev.core.domain.model.Label

class LabelsDialogFragment : DialogFragment() {
  
  interface Callback {
    
    fun onItemSelected(label: Label)
  }
  
}