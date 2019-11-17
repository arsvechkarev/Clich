package com.arsvechkarev.core.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun <T : RecyclerView.ViewHolder> RecyclerView.setupWith(adapter: RecyclerView.Adapter<T>) {
  layoutManager = LinearLayoutManager(context)
  this.adapter = adapter
}