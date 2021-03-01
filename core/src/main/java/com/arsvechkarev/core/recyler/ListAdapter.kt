package com.arsvechkarev.core.recyler

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.arsvechkarev.core.recyler.CallbackType.ALWAYS_FALSE
import com.arsvechkarev.core.recyler.CallbackType.APPENDED_LIST
import com.arsvechkarev.core.recyler.CallbackType.TWO_LISTS
import com.arsvechkarev.vault.recycler.ListAdapterDelegate
import kotlin.reflect.KClass

abstract class ListAdapter : RecyclerView.Adapter<ViewHolder>() {
  
  protected var recyclerView: RecyclerView? = null
    private set
  
  private var data: MutableList<DifferentiableItem> = ArrayList()
  
  private val classesToViewTypes = HashMap<KClass<*>, Int>()
  private val delegatesSparseArray =
    SparseArrayCompat<ListAdapterDelegate<out DifferentiableItem>>()
  private val delegates = ArrayList<ListAdapterDelegate<out DifferentiableItem>>()
  
  fun changeListWithoutAnimation(list: List<DifferentiableItem>) {
    data = ArrayList(list)
    notifyDataSetChanged()
  }
  
  fun submitList(list: List<DifferentiableItem>, callbackType: CallbackType = TWO_LISTS) {
    val callback = when (callbackType) {
      APPENDED_LIST -> AppendedListDiffCallbacks(list, data.size)
      TWO_LISTS -> TwoListsDiffCallBack(data, list)
      ALWAYS_FALSE -> AlwaysFalseCallback(data, list)
    }
    data = list as MutableList<DifferentiableItem>
    applyChanges(callback)
  }
  
  protected fun addDelegates(vararg delegates: ListAdapterDelegate<out DifferentiableItem>) {
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
  
  private fun applyChanges(callback: DiffUtil.Callback) {
    val diffResult = DiffUtil.calculateDiff(callback, false)
    diffResult.dispatchUpdatesTo(this@ListAdapter)
  }
}