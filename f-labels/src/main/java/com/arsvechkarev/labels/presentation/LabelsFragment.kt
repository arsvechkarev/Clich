package com.arsvechkarev.labels.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.inBackground
import com.arsvechkarev.core.extensions.observe
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.labels.R
import com.arsvechkarev.labels.dialog.CreateLabelDialog
import com.arsvechkarev.labels.list.DefaultLabelCallback
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.list.Mode.Default
import com.arsvechkarev.storage.database.CentralDatabase
import kotlinx.android.synthetic.main.fragment_labels.fabNewLabel
import kotlinx.android.synthetic.main.fragment_labels.recyclerLabels
import kotlinx.android.synthetic.main.fragment_labels.toolbar

class LabelsFragment : BaseFragment(), CreateLabelDialog.Callback {
  
  override val layoutId: Int = R.layout.fragment_labels
  private lateinit var layoutManager: LinearLayoutManager
  
  fun hideK() {
    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY)
  }
  
  private val adapter by lazy {
    LabelsAdapter(
      Default(object : DefaultLabelCallback {
        
        private fun endEditing() {
          fabNewLabel.visible()
          hideK()
        }
        
        override fun onStartEditing() {
          fabNewLabel.gone()
          hideK()
        }
        
        override fun onSaveLabel(label: Label, newName: String) {
          endEditing()
          label.name = newName
          inBackground {
            CentralDatabase.instance.labelsDao().update(label)
          }
        }
        
        override fun onDeletingLabel(label: Label) {
          endEditing()
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
