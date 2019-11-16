package com.arsvechkarev.info.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.ViewModelFactory
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.info.R
import javax.inject.Inject

class InfoFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.layout_info
  @Inject lateinit var viewModelFactory: ViewModelFactory
  private lateinit var viewModel: InfoViewModel
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    injectThis()
    viewModel = viewModelOf(viewModelFactory) {}
  }
  
  private fun injectThis() {
  
  }
  
}
