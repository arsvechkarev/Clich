package com.arsvechkarev.words.list

import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.assertThat
import com.arsvechkarev.core.recyler.DifferentiableItem
import com.arsvechkarev.core.recyler.BaseAdapter
import com.arsvechkarev.core.recyler.delegate
import com.arsvechkarev.words.R
import kotlinx.android.synthetic.main.item_word.view.textWord

class WordsListAdapter(
  clickListener: (Word) -> Unit = {}
) : BaseAdapter() {
  
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
  
  fun addWord(word: Word) {
    (data as MutableList<DifferentiableItem>).add(word)
    notifyItemInserted(data.lastIndex)
  }
  
  fun updateWord(word: Word) {
    var wordPosition = -1
    for (i in data.indices) {
      val item = data[i]
      if ((item as Word).id == word.id) {
        wordPosition = i
      }
    }
    assertThat(wordPosition != -1)
    (data as MutableList<DifferentiableItem>)[wordPosition] = word
    notifyItemChanged(wordPosition)
  }
  
  fun deleteWord(word: Word) {
    var wordPosition = -1
    for (i in data.indices) {
      val item = data[i]
      if ((item as Word).id == word.id) {
        wordPosition = i
      }
    }
    (data as MutableList<DifferentiableItem>).removeAt(wordPosition)
    notifyItemRemoved(wordPosition)
  }
}