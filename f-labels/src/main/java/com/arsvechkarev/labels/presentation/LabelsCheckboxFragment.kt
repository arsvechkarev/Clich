package com.arsvechkarev.labels.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.observe
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.list.Mode
import com.arsvechkarev.storage.database.CentralDatabase
import kotlinx.android.synthetic.main.fragment_labels.recyclerLabels

class LabelsCheckboxFragment : BaseFragment() {
  
  override val layoutId = R.layout.fragment_labels
  
  private lateinit var word: Word
  private lateinit var labels: List<Label>
  private val labelsAdapter by lazy { LabelsAdapter(Mode.Checkbox(word, labels)) }
  
  @Suppress("UNCHECKED_CAST")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    word = arguments!!.get(WORD_KEY) as Word
    labels = arguments!!.get(LABELS_KEY) as List<Label>
    recyclerLabels.setupWith(labelsAdapter)
    CentralDatabase.instance.labelsDao().getAll().observe(this) {
      labelsAdapter.submitList(it)
    }
  }
  
  companion object {
    
    const val WORD_KEY = "WORD_KEY"
    const val LABELS_KEY = "LABELS_KEY"
    
    fun of(word: Word, labelsList: ArrayList<Label>): LabelsCheckboxFragment {
      val bundle = Bundle()
      bundle.putParcelable(WORD_KEY, word)
      bundle.putParcelableArrayList(LABELS_KEY, labelsList)
      val fragment = LabelsCheckboxFragment()
      fragment.arguments = bundle
      return fragment
    }
  }
}