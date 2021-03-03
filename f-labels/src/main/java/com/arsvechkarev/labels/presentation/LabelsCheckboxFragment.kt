package com.arsvechkarev.labels.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.ClichApplication
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.di.DaggerLabelsCheckboxComponent
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.presentation.LabelsCheckboxState.ShowingLabels
import com.arsvechkarev.labels.presentation.LabelsCheckboxState.ShowingNoLabels
import kotlinx.android.synthetic.main.fragment_labels.fabNewLabel
import kotlinx.android.synthetic.main.fragment_labels.layoutLabelsStub
import kotlinx.android.synthetic.main.fragment_labels.recyclerLabels
import kotlinx.android.synthetic.main.fragment_labels.toolbar
import javax.inject.Inject

class LabelsCheckboxFragment : BaseFragment(), LabelCheckedCallback {
  
  override val layoutId = R.layout.fragment_labels
  
  @Inject lateinit var labelsAdapter: LabelsAdapter
  @Inject lateinit var viewModel: LabelsCheckboxViewModel
  
  @Suppress("UNCHECKED_CAST")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val word = arguments!!.get(WORD_KEY) as Word
    DaggerLabelsCheckboxComponent.builder()
      .coreComponent(ClichApplication.coreComponent)
      .labelsCheckboxFragment(this)
      .inputWord(word)
      .build()
      .inject(this)
    setupViews()
    viewModel.state.observe(this, this::handleState)
    viewModel.fetchLabelsForWord()
  }
  
  private fun handleState(state: LabelsCheckboxState) {
    when (state) {
      is ShowingLabels -> {
        layoutLabelsStub.gone()
        recyclerLabels.visible()
        labelsAdapter.submitList(state.allLabels)
      }
      ShowingNoLabels -> {
        layoutLabelsStub.visible()
        recyclerLabels.gone()
      }
    }
  }
  
  override fun isLabelChecked(label: Label): Boolean {
    return viewModel.isLabelChecked(label)
  }
  
  override fun onLabelChecked(label: Label) {
    viewModel.onLabelChecked(label)
  }
  
  override fun onLabelUnchecked(label: Label) {
    viewModel.onLabelUnchecked(label)
  }
  
  private fun setupViews() {
    fabNewLabel.gone()
    toolbar.setNavigationOnClickListener {
      popBackStack()
    }
    recyclerLabels.setupWith(labelsAdapter)
  }
  
  companion object {
    
    const val WORD_KEY = "WORD_KEY"
    
    fun of(word: Word?) = LabelsCheckboxFragment().apply {
      arguments = Bundle().apply {
        putParcelable(WORD_KEY, word)
      }
    }
  }
}