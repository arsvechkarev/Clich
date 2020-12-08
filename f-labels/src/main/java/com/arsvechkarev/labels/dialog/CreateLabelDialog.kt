package com.arsvechkarev.labels.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.arsvechkarev.core.extensions.onTextChanged
import com.arsvechkarev.core.extensions.showKeyboard
import com.arsvechkarev.core.extensions.string
import com.arsvechkarev.labels.R

class CreateLabelDialog : DialogFragment() {
  
  private lateinit var editTextLabelName: EditText
  private lateinit var callback: Callback
  
  override fun onAttach(context: Context) {
    super.onAttach(context)
    callback = try {
      activity as Callback
    } catch (e: ClassCastException) {
      try {
        parentFragment as Callback
      } catch (e: ClassCastException) {
        throw IllegalStateException("Either activity of fragment should implement Callback", e)
      }
    }
  }
  
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
  }
  
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val view = requireActivity().layoutInflater.inflate(R.layout.dialog_create_label, null)
    val buttonCreate = view.findViewById<Button>(R.id.buttonCreate)
    buttonCreate.isEnabled = false
    editTextLabelName = view.findViewById(R.id.editTextLabelName)
    editTextLabelName.onTextChanged {
      buttonCreate.isEnabled = !it.isBlank()
    }
    buttonCreate.setOnClickListener {
      callback.onLabelCreated(editTextLabelName.string())
      dismiss()
    }
    return AlertDialog.Builder(activity!!)
      .setView(view)
      .create()
  }
  
  override fun onResume() {
    super.onResume()
    editTextLabelName.requestFocus()
    showKeyboard()
  }
  
  interface Callback {
    
    fun onLabelCreated(labelName: String)
  }
}