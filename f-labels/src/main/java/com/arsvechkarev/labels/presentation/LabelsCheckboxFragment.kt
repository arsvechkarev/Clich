package com.arsvechkarev.labels.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
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
  private val labelsAdapter by lazy { LabelsAdapter(Mode.Checkbox(word)) }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    word = arguments!!.get(WORD_KEY) as Word
    recyclerLabels.setupWith(labelsAdapter)
    CentralDatabase.instance.labelsDao().getAll().observe(this) {
      labelsAdapter.submitList(it)
    }
  }
  
  companion object {
    
    const val WORD_KEY = "WORD_KEY"
    
    fun of(word: Word): LabelsCheckboxFragment {
      val bundle = Bundle()
      bundle.putParcelable(WORD_KEY, word)
      val fragment = LabelsCheckboxFragment()
      fragment.arguments = bundle
      return fragment
    }
  }
}