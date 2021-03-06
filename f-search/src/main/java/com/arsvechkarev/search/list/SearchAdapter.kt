package com.arsvechkarev.search.list

import android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import android.widget.TextView.BufferType.SPANNABLE
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.recyler.BaseAdapter
import com.arsvechkarev.core.recyler.delegate
import com.arsvechkarev.search.R

class SearchAdapter(
  private val clickListener: (Word) -> Unit = {}
) : BaseAdapter() {
  
  var searchedText: String? = null
  
  fun submitList(list: List<Word>, searchedText: String = "") {
    this.searchedText = searchedText
    changeListWithoutAnimation(list)
  }
  
  init {
    addDelegates(
      delegate<Word> {
        layoutRes(R.layout.item_word)
        onInitViewHolder {
          itemView.setOnClickListener { clickListener(item) }
        }
        onBind {
          val textWord = itemView as TextView
          val searchedText = searchedText
          if (searchedText != null) {
            val startIndex = item.name.indexOf(searchedText, ignoreCase = true)
            if (startIndex == -1) {
              textWord.text = item.name
              return@onBind
            }
            val endIndex = startIndex + searchedText.length
            val spannable = SpannableString(item.name)
            spannable.setSpan(
              ForegroundColorSpan(itemView.context.getColor(R.color.light_colorHighlight)),
              startIndex,
              endIndex,
              SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textWord.setText(spannable, SPANNABLE)
          } else {
            textWord.text = item.name
          }
        }
      }
    )
  }
}