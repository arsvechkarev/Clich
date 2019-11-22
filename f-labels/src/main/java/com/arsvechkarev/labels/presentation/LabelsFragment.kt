package com.arsvechkarev.labels.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.inBackground
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.dialog.CreateLabelDialog
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.storage.database.CentralDatabase
import kotlinx.android.synthetic.main.fragment_labels.fabNewLabel
import kotlinx.android.synthetic.main.fragment_labels.recyclerLabels

class LabelsFragment : BaseFragment(), CreateLabelDialog.Callback {
  
  override val layoutId: Int = R.layout.fragment_labels
  
  private val adapter = LabelsAdapter()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    recyclerLabels.setupWith(adapter)
    CentralDatabase.instance.labelsDao().getAllLive().observe(this, Observer {
      adapter.submitList(it)
    })
    fabNewLabel.setOnClickListener {
      CreateLabelDialog().show(childFragmentManager, null)
    }
  }
  
  override fun onCreateClick(labelName: String) {
    inBackground {
      CentralDatabase.instance.labelsDao().create(Label(name = labelName))
    }
  }
  
}
