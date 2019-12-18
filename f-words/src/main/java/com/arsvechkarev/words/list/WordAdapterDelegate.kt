package com.arsvechkarev.words.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.inflate
import com.arsvechkarev.core.recyler.AdapterDelegate
import com.arsvechkarev.core.recyler.DisplayableItem
import com.arsvechkarev.words.R
import kotlinx.android.synthetic.main.item_word.view.textWord

class WordAdapterDelegate(private val clickListener: (Word) -> Unit) : AdapterDelegate {
  
  override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return WordsListViewHolder(parent.inflate(R.layout.item_word), clickListener)
  }
  
  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DisplayableItem) {
    (holder as WordsListViewHolder).bind(item as Word)
  }
  
  class WordsListViewHolder(
    itemView: View,
    val clickListener: (Word) -> Unit
  ) : RecyclerView.ViewHolder(itemView) {
    
    fun bind(item: Word) {
      itemView.setOnClickListener { clickListener(item) }
      itemView.textWord.text = item.name
    }
  }
}