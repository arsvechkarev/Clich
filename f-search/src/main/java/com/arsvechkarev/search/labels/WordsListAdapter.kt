package com.arsvechkarev.search.labels

import android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView.BufferType.SPANNABLE
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
  
  var searchedText: String? = null
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsListViewHolder {
    return WordsListViewHolder(parent.inflate(R.layout.item_word))
  }
  
  override fun onBindViewHolder(holder: WordsListViewHolder, position: Int) {
    holder.bind(getItem(position), searchedText)
  }
  
  fun submitList(list: List<Word>?, searchedText: String) {
    this.searchedText = searchedText
    super.submitList(list)
    notifyDataSetChanged()
  }
  
  override fun submitList(list: List<Word>?) {
    searchedText = ""
    super.submitList(list)
    notifyDataSetChanged()
  }
  
  inner class WordsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    
    fun bind(item: Word, searchedText: String?) {
      itemView.setOnClickListener { clickListener(item) }
      if (searchedText != null) {
        val startIndex = item.name.indexOf(searchedText)
        
        if (startIndex == -1) {
          itemView.textWord.text = item.name
          return
        }
        
        val endIndex = startIndex + searchedText.length
        val spannable = SpannableString(item.name)
        spannable.setSpan(
          ForegroundColorSpan(itemView.context.getColor(R.color.light_colorHighlight)),
          startIndex,
          endIndex,
          SPAN_EXCLUSIVE_EXCLUSIVE
        )
        itemView.textWord.setText(spannable, SPANNABLE)
      } else {
        itemView.textWord.text = item.name
      }
    }
  }
}