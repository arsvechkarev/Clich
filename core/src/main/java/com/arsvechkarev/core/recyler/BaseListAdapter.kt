package com.arsvechkarev.core.recyler

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseListAdapter :
  ListAdapter<DisplayableItem, ViewHolder>(DisplayableItem.DiffCallBack()) {
  
  var data: List<DisplayableItem> = ArrayList()
  val delegates = SparseArrayCompat<AdapterDelegate>()
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return delegates[viewType]!!.onCreateViewHolder(parent)
  }
  
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    delegates[getItemViewType(position)]!!.onBindViewHolder(holder, data[position])
  }
  
  override fun getItemViewType(position: Int): Int {
    return data[position].type
  }
  
  override fun submitList(list: List<DisplayableItem>?) {
    data = list ?: ArrayList()
    notifyDataSetChanged()
    super.submitList(list)
  }
}