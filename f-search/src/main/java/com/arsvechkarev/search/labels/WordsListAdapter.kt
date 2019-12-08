package com.arsvechkarev.search.labels

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.DisplayableItem.DiffCallBack
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.inflate
import com.arsvechkarev.search.R
import com.arsvechkarev.search.labels.WordsListAdapter.WordsListViewHolder
import kotlinx.android.synthetic.main.item_word.view.textWord

class WordsListAdapter(
  private val clickListener: (Word) -> Unit = {}
) : ListAdapter<Word, WordsListViewHolder>(DiffCallBack()) {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsListViewHolder {
    return WordsListViewHolder(parent.inflate(R.layout.item_word))
  }
  
  override fun onBindViewHolder(holder: WordsListViewHolder, position: Int) {
    holder.bind(getItem(position))
  }
  
  override fun submitList(list: List<Word>?) {
    super.submitList(list)
    notifyDataSetChanged()
  }
  
  inner class WordsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    
    fun bind(item: Word) {
      itemView.setOnClickListener { clickListener(item) }
      itemView.textWord.text = item.word
    }
  }
}