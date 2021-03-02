package com.arsvechkarev.core.recyler

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.arsvechkarev.core.recyler.CallbackType.ALWAYS_FALSE
import com.arsvechkarev.core.recyler.CallbackType.APPENDED_LIST
import com.arsvechkarev.core.recyler.CallbackType.TWO_LISTS
import com.arsvechkarev.vault.recycler.ListAdapterDelegate
import java.util.concurrent.Executors
import kotlin.reflect.KClass

abstract class ListAdapter : RecyclerView.Adapter<ViewHolder>() {
  
  private val backgroundExecutor = Executors.newSingleThreadExecutor()
  private val handler = Handler(Looper.getMainLooper())
  
  private val classesToViewTypes = HashMap<KClass<*>, Int>()
  private val delegates = ArrayList<ListAdapterDelegate<out DifferentiableItem>>()
  private val delegatesSparseArray =
    SparseArrayCompat<ListAdapterDelegate<out DifferentiableItem>>()
  
  protected var data: List<DifferentiableItem> = ArrayList()
  
  protected var recyclerView: RecyclerView? = null
    private set
  
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
    backgroundExecutor.submit {
      val diffResult = DiffUtil.calculateDiff(callback, false)
      handler.post {
        diffResult.dispatchUpdatesTo(this@ListAdapter)
        data = list
      }
    }
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
}