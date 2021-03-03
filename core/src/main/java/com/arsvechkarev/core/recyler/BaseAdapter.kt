package com.arsvechkarev.core.recyler

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlin.reflect.KClass

abstract class BaseAdapter : RecyclerView.Adapter<ViewHolder>() {
  
  private val classesToViewTypes = HashMap<KClass<*>, Int>()
  private val delegates = ArrayList<AdapterDelegate<out DisplayableItem>>()
  private val delegatesSparseArray =
    SparseArrayCompat<AdapterDelegate<out DisplayableItem>>()
  
  protected var data: List<DisplayableItem> = ArrayList()
  
  protected var recyclerView: RecyclerView? = null
    private set
  
  fun changeListWithoutAnimation(list: List<DisplayableItem>) {
    data = ArrayList(list)
    notifyDataSetChanged()
  }
  
  protected fun addDelegates(vararg delegates: AdapterDelegate<out DisplayableItem>) {
    this.delegates.addAll(delegates)
    delegates.forEachIndexed { i, delegate ->
      classesToViewTypes[delegate.modelClass] = i
      delegatesSparseArray.put(i, delegate)
    }
  }
  
  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    this.recyclerView = recyclerView
    delegates.forEach { it.onAttachedToRecyclerView(recyclerView) }
  }
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val delegate = delegatesSparseArray[viewType] ?: error("No delegate for view type $viewType")
    return delegate.onCreateViewHolder(parent)
  }
  
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val adapterDelegate = delegatesSparseArray[getItemViewType(position)]
      ?: error("No delegate for position $position")
    adapterDelegate.onBindViewHolderRaw(holder, data[position])
  }
  
  override fun getItemViewType(position: Int): Int {
    return classesToViewTypes[data[position]::class] ?: error(
      "Can't find delegate for position: $position"
    )
  }
  
  override fun getItemCount(): Int {
    return data.size
  }
  
  override fun onViewRecycled(holder: ViewHolder) {
    (holder as? DelegateViewHolder<*>)?.onViewRecycled()
  }
  
  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    delegates.forEach { it.onDetachedFromRecyclerView(recyclerView) }
  }
}