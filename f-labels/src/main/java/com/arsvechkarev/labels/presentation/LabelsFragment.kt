package com.arsvechkarev.labels.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.domain.model.LabelEntity
import com.arsvechkarev.core.extensions.inBackground
import com.arsvechkarev.core.extensions.string
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.storage.database.CentralDatabase
import kotlinx.android.synthetic.main.fragment_labels.buttonSaveNewLabel
import kotlinx.android.synthetic.main.fragment_labels.editTextNewLabel

class LabelsFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.fragment_labels
  
  private val adapter = LabelsAdapter()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    CentralDatabase.instance.labelsDao().getAllLive().observe(this, Observer {
      adapter.submitList(it)
    })
    buttonSaveNewLabel.setOnClickListener {
      inBackground {
        CentralDatabase.instance.labelsDao().create(LabelEntity(name = editTextNewLabel.string()))
      }
    }
    
  }
  
}