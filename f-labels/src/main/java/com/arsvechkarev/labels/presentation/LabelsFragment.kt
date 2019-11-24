package com.arsvechkarev.labels.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.inBackground
import com.arsvechkarev.core.extensions.observe
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.showKeyboard
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.dialog.CreateLabelDialog
import com.arsvechkarev.labels.list.DefaultLabelCallback
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.list.Mode.Default
import com.arsvechkarev.storage.database.CentralDatabase
import kotlinx.android.synthetic.main.fragment_labels.fabNewLabel
import kotlinx.android.synthetic.main.fragment_labels.recyclerLabels
import kotlinx.android.synthetic.main.fragment_labels.toolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LabelsFragment : BaseFragment(), CreateLabelDialog.Callback {
  
  override val layoutId: Int = R.layout.fragment_labels
  
  private lateinit var layoutManager: LinearLayoutManager
  
  private val adapter by lazy {
    LabelsAdapter(
      Default(object : DefaultLabelCallback {
        override fun onStartEditing() {
          showKeyboard()
        }
        
        override fun onSaveLabel(label: Label, newName: String) {
          label.name = newName
          inBackground {
            CentralDatabase.instance.labelsDao().update(label)
          }
        }
        
        override fun onDeletingLabel(label: Label) {
          inBackground {
            CentralDatabase.instance.labelsDao().delete(label)
          }
        }
      })
    )
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    layoutManager = LinearLayoutManager(context)
    recyclerLabels.layoutManager = layoutManager
    recyclerLabels.adapter = adapter
    toolbar.setNavigationOnClickListener { popBackStack() }
    CentralDatabase.instance.labelsDao().getAll().observe(this, adapter::submitList)
    GlobalScope.launch(Dispatchers.Main) {
      val list = CentralDatabase.instance.labelsDao().getAllSuspend()
      adapter.submitList(list)
    }
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
