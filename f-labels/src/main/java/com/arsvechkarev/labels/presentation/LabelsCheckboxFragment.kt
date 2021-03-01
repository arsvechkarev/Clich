package com.arsvechkarev.labels.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.CentralDatabase
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.list.Mode
import com.arsvechkarev.labels.list.viewholders.CheckedChangedCallback
import kotlinx.android.synthetic.main.fragment_labels.fabNewLabel
import kotlinx.android.synthetic.main.fragment_labels.layoutLabelsStub
import kotlinx.android.synthetic.main.fragment_labels.recyclerLabels
import kotlinx.android.synthetic.main.fragment_labels.toolbar

class LabelsCheckboxFragment : BaseFragment() {
  
  lateinit var callback: CheckedChangedCallback
  
  override val layoutId = R.layout.fragment_labels
  
  private var word: Word? = null
  private lateinit var alreadySelectedLabels: List<Label>
  private lateinit var labelsAdapter: LabelsAdapter
  
  @Suppress("UNCHECKED_CAST")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    fabNewLabel.gone()
    word = arguments!!.get(WORD_KEY) as Word?
    alreadySelectedLabels = arguments!!.get(LABELS_KEY) as List<Label>
    toolbar.setNavigationOnClickListener {
      popBackStack()
    }
    labelsAdapter = if (word == null) {
      LabelsAdapter(Mode.CheckboxNotCreatedWord(callback, alreadySelectedLabels))
    } else {
      LabelsAdapter(Mode.Checkbox(word!!, alreadySelectedLabels))
    }
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
    const val LABELS_KEY = "LABELS_KEY"
    
    fun of(word: Word, labelsList: ArrayList<Label>): LabelsCheckboxFragment {
      val bundle = Bundle()
      bundle.putParcelable(WORD_KEY, word)
      bundle.putParcelableArrayList(LABELS_KEY, labelsList)
      val fragment = LabelsCheckboxFragment()
      fragment.arguments = bundle
      return fragment
    }
    
    fun of(labelsList: ArrayList<Label>): LabelsCheckboxFragment {
      val bundle = Bundle()
      bundle.putParcelableArrayList(LABELS_KEY, labelsList)
      val fragment = LabelsCheckboxFragment()
      fragment.arguments = bundle
      return fragment
    }
  }
}