package com.arsvechkarev.labels.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.featurecommon.LabelsWithCheckboxState
import com.arsvechkarev.featurecommon.LabelsWithCheckboxState.ShowingLabelsWithCheckbox
import com.arsvechkarev.featurecommon.LabelsWithCheckboxState.ShowingNoLabelsWithCheckbox
import com.arsvechkarev.featurecommon.WordInfoComponentHolder.wordInfoComponent
import com.arsvechkarev.featurecommon.WordInfoViewModel
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.di.DaggerLabelsCheckboxComponent
import com.arsvechkarev.labels.list.LabelsAdapter
import kotlinx.android.synthetic.main.fragment_labels.fabNewLabel
import kotlinx.android.synthetic.main.fragment_labels.layoutLabelsStub
import kotlinx.android.synthetic.main.fragment_labels.recyclerLabels
import kotlinx.android.synthetic.main.fragment_labels.toolbar
import javax.inject.Inject

class LabelsCheckboxFragment : BaseFragment(), LabelCheckedCallback {
  
  override val layoutId = R.layout.fragment_labels
  
  @Inject lateinit var labelsAdapter: LabelsAdapter
  @Inject lateinit var viewModel: WordInfoViewModel
  
  @Suppress("UNCHECKED_CAST")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    DaggerLabelsCheckboxComponent.builder()
      .wordInfoComponent(wordInfoComponent)
      .labelsCheckboxFragment(this)
      .build()
      .inject(this)
    setupViews()
    viewModel.labelsState.observe(this, this::handleState)
    viewModel.fetchAllLabels()
  }
  
  private fun handleState(state: LabelsWithCheckboxState) {
    when (state) {
      is ShowingLabelsWithCheckbox -> {
        layoutLabelsStub.gone()
        recyclerLabels.visible()
        labelsAdapter.submitList(state.allLabels)
      }
      ShowingNoLabelsWithCheckbox -> {
        layoutLabelsStub.visible()
        recyclerLabels.gone()
      }
    }
  }
  
  override fun isLabelChecked(label: Label): Boolean {
    return viewModel.isLabelChecked(label)
  }
  
  override fun onLabelChecked(label: Label) {
    viewModel.addLabel(label)
  }
  
  override fun onLabelUnchecked(label: Label) {
    viewModel.removeLabel(label)
  }
  
  private fun setupViews() {
    fabNewLabel.gone()
    toolbar.setNavigationOnClickListener {
      popBackStack()
    }
    recyclerLabels.setupWith(labelsAdapter)
  }
}