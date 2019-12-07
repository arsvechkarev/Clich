package com.arsvechkarev.labels.list.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.invisible
import com.arsvechkarev.core.extensions.string
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.list.DefaultLabelCallback
import kotlinx.android.synthetic.main.item_label_default.view.dividerBottom
import kotlinx.android.synthetic.main.item_label_default.view.dividerTop
import kotlinx.android.synthetic.main.item_label_default.view.editTextLabel
import kotlinx.android.synthetic.main.item_label_default.view.imageEnd
import kotlinx.android.synthetic.main.item_label_default.view.imageStart
import kotlinx.android.synthetic.main.item_label_default.view.textLabel

class DefaultLabelViewHolder(
  itemView: View,
  private val labelCallback: DefaultLabelCallback
) : RecyclerView.ViewHolder(itemView) {
  
  private var isEditingMode = false
  
  fun bind(item: Label) {
    itemView.editTextLabel.setOnFocusChangeListener { _, hasFocus ->
      if (!hasFocus) {
        endEditingMode()
      }
    }
    itemView.textLabel.text = item.name
    itemView.imageStart.setOnClickListener {
      if (isEditingMode) {
        endEditingMode()
        labelCallback.onDeletingLabel(item)
      }
    }
    itemView.imageEnd.setOnClickListener {
      isEditingMode = !isEditingMode
      if (isEditingMode) {
        labelCallback.onStartEditing()
        startEditingMode()
      } else {
        labelCallback.onSaveLabel(item, itemView.editTextLabel.string())
        endEditingMode()
      }
    }
  }
  
  private fun startEditingMode() {
    itemView.dividerTop.visible()
    itemView.dividerBottom.visible()
    itemView.imageEnd.setImageResource(R.drawable.ic_checkmark)
    itemView.imageStart.setImageResource(R.drawable.ic_delete)
    itemView.editTextLabel.apply {
      setText(itemView.textLabel.text.toString())
      visible()
      requestFocus()
    }
    itemView.textLabel.invisible()
  }
  
  private fun endEditingMode() {
    itemView.dividerTop.gone()
    itemView.dividerBottom.gone()
    itemView.imageStart.setImageResource(R.drawable.ic_label)
    itemView.imageEnd.setImageResource(R.drawable.ic_edit)
    itemView.editTextLabel.apply {
      invisible()
      text.clear()
      clearFocus()
    }
    itemView.textLabel.visible()
  }
}