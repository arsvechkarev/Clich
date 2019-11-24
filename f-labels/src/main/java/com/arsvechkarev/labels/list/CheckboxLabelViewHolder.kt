package com.arsvechkarev.labels.list

import android.util.Log
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
  private val word: Word
) : RecyclerView.ViewHolder(itemView) {
  
  fun bind(item: Label) {
    itemView.textLabel.text = item.name
    itemView.checkbox.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        inBackground {
          Log.d("zxcvb", "is checked")
          CentralDatabase.instance.wordsAndLabelsDao().insert(
            WordsLabelsJoin(word.id!!, item.id!!)
          )
        }
      } else {
        inBackground {
          Log.d("zxcvb", "is unchecked")
          CentralDatabase.instance.wordsAndLabelsDao().delete(
            WordsLabelsJoin(word.id!!, item.id!!)
          )
        }
      }
    }
  }
}