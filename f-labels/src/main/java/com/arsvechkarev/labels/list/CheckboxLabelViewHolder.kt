package com.arsvechkarev.labels.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.WordsLabelsJoin
import com.arsvechkarev.core.extensions.inBackground
import com.arsvechkarev.storage.database.CentralDatabase
import kotlinx.android.synthetic.main.item_label_checkbox.view.checkbox
import kotlinx.android.synthetic.main.item_label_checkbox.view.textLabel

class CheckboxLabelViewHolder(
  itemView: View,
  private val word: Word,
  private val labels: List<Label>
) : RecyclerView.ViewHolder(itemView) {
  
  fun bind(item: Label) {
    if (labels.contains(item)) {
      itemView.checkbox.isChecked = true
    }
    itemView.textLabel.text = item.name
    itemView.checkbox.setOnCheckedChangeListener { _, isChecked ->
      inBackground {
        val wordsLabelsJoin = WordsLabelsJoin(word.id!!, item.id!!)
        if (isChecked) {
          CentralDatabase.instance.wordsAndLabelsDao().insert(wordsLabelsJoin)
        } else {
          CentralDatabase.instance.wordsAndLabelsDao().delete(wordsLabelsJoin)
        }
      }
    }
  }
}