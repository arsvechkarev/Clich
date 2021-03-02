package com.arsvechkarev.labels.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.CentralDatabase
import com.arsvechkarev.core.CheckedChangedCallback
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.list.LabelsAdapter
import kotlinx.android.synthetic.main.fragment_labels.fabNewLabel
import kotlinx.android.synthetic.main.fragment_labels.layoutLabelsStub
import kotlinx.android.synthetic.main.fragment_labels.recyclerLabels
import kotlinx.android.synthetic.main.fragment_labels.toolbar

class LabelsCheckboxFragment : BaseFragment() {
  
  lateinit var callback: CheckedChangedCallback
  
  override val layoutId = R.layout.fragment_labels
  
  private var word: Word? = null
  
  private lateinit var labelsAdapter: LabelsAdapter
  
  @Suppress("UNCHECKED_CAST")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    fabNewLabel.gone()
    word = arguments!!.get(WORD_KEY) as Word?
    //    alreadySelectedLabels = arguments!!.get(LABELS_KEY) as List<Label>
    toolbar.setNavigationOnClickListener {
      popBackStack()
    }
    //    labelsAdapter = LabelsAdapter(Mode.Checkbox(alreadySelectedLabels, callback))
    recyclerLabels.setupWith(labelsAdapter)
    CentralDatabase.instance.labelsDao().getAll().observe(this) {
      if (it.isEmpty()) {
        layoutLabelsStub.visible()
        recyclerLabels.gone()
      } else {
        layoutLabelsStub.gone()
        recyclerLabels.visible()
        labelsAdapter.submitList(it)
      }
    }
  }
  
  companion object {
    
    const val WORD_KEY = "WORD_KEY"
    
    fun of(word: Word?): LabelsCheckboxFragment {
      val bundle = Bundle()
      bundle.putParcelable(WORD_KEY, word)
      val fragment = LabelsCheckboxFragment()
      fragment.arguments = bundle
      return fragment
    }
  }
}