package com.arsvechkarev.core

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arsvechkarev.core.domain.model.Word

fun List<Word>.applyAllWordsSorting(): List<Word> {
  return this.sortedBy { it.creationDate }
}

fun RecyclerView.applyAllWordsListStyle(adapter: RecyclerView.Adapter<*>) {
  layoutManager = LinearLayoutManager(context).apply {
    stackFromEnd = true
    reverseLayout = true
  }
  this.adapter = adapter
}