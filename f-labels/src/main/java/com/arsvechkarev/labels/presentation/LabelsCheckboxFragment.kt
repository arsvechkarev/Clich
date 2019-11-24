package com.arsvechkarev.labels.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.extensions.observe
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.list.CheckboxLabelCallback
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.list.Mode
import com.arsvechkarev.storage.database.CentralDatabase
import kotlinx.android.synthetic.main.fragment_labels.recyclerLabels

class LabelsCheckboxFragment : BaseFragment() {
  
  override val layoutId = R.layout.fragment_labels
  private lateinit var checkboxLabelCallback: CheckboxLabelCallback
  
  fun setCallback(checkboxLabelCallback: CheckboxLabelCallback) {
    this.checkboxLabelCallback = checkboxLabelCallback
  }
  
  private val labelsAdapter by lazy { LabelsAdapter(Mode.Checkbox(checkboxLabelCallback)) }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    recyclerLabels.setupWith(labelsAdapter)
    CentralDatabase.instance.labelsDao().getAll().observe(this) {
      labelsAdapter.submitList(it)
    }
  }
}