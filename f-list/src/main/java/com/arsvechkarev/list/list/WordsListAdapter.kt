package com.arsvechkarev.list.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.inflate
import com.arsvechkarev.list.R
import kotlinx.android.synthetic.main.item_word.view.textWord

class WordsListAdapter(
  private val clickListener: (Word) -> Unit = {}
) : RecyclerView.Adapter<WordsListAdapter.WordsListViewHolder>() {
  
  private var data: List<Word> = ArrayList()
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsListViewHolder {
    return WordsListViewHolder(parent.inflate(R.layout.item_word))
  }
  
  override fun getItemCount() = data.size
  
  override fun onBindViewHolder(holder: WordsListViewHolder, position: Int) {
    holder.bind(data[position], clickListener)
  }
  
  fun submitList(list: List<Word>) {
    data = list
    notifyDataSetChanged()
  }
  
  class WordsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Word, clickListener: (Word) -> Unit) {
      itemView.setOnClickListener { clickListener(item) }
      itemView.textWord.text = item.word
    }
  }
}
