package com.arsvechkarev.words.list

import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.recyler.ListAdapter
import com.arsvechkarev.core.recyler.delegate
import com.arsvechkarev.words.R
import kotlinx.android.synthetic.main.item_word.view.textWord

class WordsListAdapter(
  clickListener: (Word) -> Unit = {}
) : ListAdapter() {
  
  init {
    addDelegates(
      delegate<Word> {
        layoutRes(R.layout.item_word)
        onInitViewHolder {
          itemView.setOnClickListener { clickListener(item) }
        }
        onBind {
          itemView.textWord.text = item.name
        }
      }
    )
  }
}