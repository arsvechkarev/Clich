package com.arsvechkarev.labels.list.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.invisible
import com.arsvechkarev.core.extensions.string
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.list.Mode
import kotlinx.android.synthetic.main.item_label_default.view.dividerBottom
import kotlinx.android.synthetic.main.item_label_default.view.dividerTop
import kotlinx.android.synthetic.main.item_label_default.view.editTextLabel
import kotlinx.android.synthetic.main.item_label_default.view.imageEdit
import kotlinx.android.synthetic.main.item_label_default.view.imageSave
import kotlinx.android.synthetic.main.item_label_default.view.textLabel

class DefaultLabelViewHolder(
  itemView: View,
  private val listGetter: () -> MutableList<Label>,
  private val mode: Mode.Default
) : RecyclerView.ViewHolder(itemView) {
  
  init {
    setOnFocusChangedListener(itemView)
    setOnEditClickListener(itemView)
    setOnSaveClickListener(itemView)
  }
  
  fun bind(item: Label, position: Int) {
    itemView.textLabel.text = item.name
    if (mode.labelEditingCallback.getCurrentlyEditingLabelPosition() == position) {
      startEditingMode(requestFocus = false)
    } else {
      endEditingMode()
    }
  }
  
  private fun setOnFocusChangedListener(itemView: View) {
    itemView.editTextLabel.setOnFocusChangeListener { _, hasFocus ->
      if (!hasFocus) {
        endEditingMode()
        mode.labelEditingCallback.onEndEditing(adapterPosition)
      }
    }
  }
  
  private fun setOnEditClickListener(itemView: View) {
    itemView.imageEdit.setOnClickListener {
      val item = listGetter()[adapterPosition]
      if (mode.labelEditingCallback.getCurrentlyEditingLabelPosition() != -1) {
        endEditingMode()
        mode.labelEditingCallback.onDeleteLabel(item)
        mode.labelEditingCallback.onEndEditing(adapterPosition)
      }
    }
  }
  
  private fun setOnSaveClickListener(itemView: View) {
    itemView.imageSave.setOnClickListener {
      val isEditingMode =
        mode.labelEditingCallback.getCurrentlyEditingLabelPosition() == adapterPosition
      val item = listGetter()[adapterPosition]
      var labelName = itemView.editTextLabel.string()
      if (isEditingMode) {
        endEditingMode()
        mode.labelEditingCallback.onEndEditing(adapterPosition)
        if (labelName == item.name) {
          return@setOnClickListener
        }
        if (labelName.isBlank()) {
          labelName = item.name
        }
        mode.labelEditingCallback.onUpdateLabel(item, labelName)
        itemView.textLabel.text = labelName
      } else {
        mode.labelEditingCallback.onStartEditing(adapterPosition)
        startEditingMode(requestFocus = true)
      }
    }
  }
  
  private fun startEditingMode(requestFocus: Boolean) {
    itemView.dividerTop.visible()
    itemView.dividerBottom.visible()
    itemView.imageSave.setImageResource(R.drawable.ic_checkmark)
    itemView.imageEdit.setImageResource(R.drawable.ic_delete)
    itemView.editTextLabel.apply {
      val text = itemView.textLabel.text.toString()
      setText(text)
      setSelection(text.length)
      visible()
      if (requestFocus) {
        requestFocus()
      }
    }
    itemView.textLabel.invisible()
  }
  
  private fun endEditingMode() {
    itemView.dividerTop.gone()
    itemView.dividerBottom.gone()
    itemView.imageEdit.setImageResource(R.drawable.ic_label)
    itemView.imageSave.setImageResource(R.drawable.ic_edit)
    itemView.editTextLabel.apply {
      invisible()
      text.clear()
      clearFocus()
    }
    itemView.textLabel.visible()
  }
}