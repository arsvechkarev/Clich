package com.arsvechkarev.labels.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.ClichApplication
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.animateGone
import com.arsvechkarev.core.extensions.animateVisible
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.hideKeyboard
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.showKeyboard
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.di.DaggerLabelsComponent
import com.arsvechkarev.labels.dialog.CreateLabelDialog
import com.arsvechkarev.labels.list.LabelEditingCallback
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.presentation.LabelsState.ShowingLabels
import com.arsvechkarev.labels.presentation.LabelsState.ShowingNoLabels
import kotlinx.android.synthetic.main.fragment_labels.fabNewLabel
import kotlinx.android.synthetic.main.fragment_labels.layoutLabelsStub
import kotlinx.android.synthetic.main.fragment_labels.recyclerLabels
import kotlinx.android.synthetic.main.fragment_labels.toolbar
import javax.inject.Inject

class LabelsFragment : BaseFragment(), CreateLabelDialog.Callback, LabelEditingCallback {
  
  override val layoutId: Int = R.layout.fragment_labels
  
  @Inject lateinit var viewModel: LabelsViewModel
  @Inject lateinit var adapter: LabelsAdapter
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    DaggerLabelsComponent.builder()
      .coreComponent(ClichApplication.coreComponent)
      .labelsFragment(this)
      .build()
      .inject(this)
    setupViews()
    viewModel.fetchLabels().observe(this, this::handleState)
  }
  
  override fun onLabelNameEntered(labelName: String) {
    viewModel.createLabel(labelName)
  }
  
  override fun getCurrentlyEditingLabelPosition(): Int {
    return viewModel.currentlyEditingPosition
  }
  
  override fun onStartEditing(position: Int) {
    viewModel.currentlyEditingPosition = position
    fabNewLabel.gone()
    showKeyboard()
  }
  
  override fun onEndEditing(position: Int) {
    viewModel.currentlyEditingPosition = -1
    fabNewLabel.visible()
    hideKeyboard()
  }
  
  override fun onUpdateLabel(label: Label, newName: String) {
    viewModel.updateLabel(label, newName)
  }
  
  override fun onDeleteLabel(label: Label) {
    viewModel.deleteLabel(label)
  }
  
  private fun handleState(state: LabelsState) {
    when (state) {
      ShowingNoLabels -> {
        layoutLabelsStub.animateVisible()
        recyclerLabels.animateGone()
      }
      is ShowingLabels -> {
        layoutLabelsStub.animateGone()
        recyclerLabels.animateVisible()
        if (state.createOrDeleteHappened) {
          // If create or delete happened, submitting new list
          adapter.submitList(ArrayList(state.labels))
        } else {
          // If create or delete happened, submitting new list
          adapter.changeListQuietly(state.labels)
        }
      }
    }
  }
  
  private fun setupViews() {
    val layoutManager = LinearLayoutManager(context)
    recyclerLabels.layoutManager = layoutManager
    recyclerLabels.adapter = adapter
    toolbar.setNavigationOnClickListener {
      popBackStack()
      hideKeyboard()
    }
    fabNewLabel.setOnClickListener {
      CreateLabelDialog().show(childFragmentManager, null)
    }
  }
}