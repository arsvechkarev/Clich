package com.arsvechkarev.core.recyler

import androidx.recyclerview.widget.DiffUtil

/**
 * Callback for updating items in recycler view
 */
class DiffCallBack<T : DifferentiableItem> : DiffUtil.ItemCallback<T>() {
  
  override fun areItemsTheSame(oldItem: T, newItem: T) =
    oldItem.id == newItem.id
  
  override fun areContentsTheSame(oldItem: T, newItem: T) =
    oldItem == newItem
}