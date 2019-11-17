package com.arsvechkarev.list.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.di.viewmodel.ViewModelFactory
import com.arsvechkarev.core.extensions.observe
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.showToast
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.list.R
import com.arsvechkarev.list.list.WordsListAdapter
import kotlinx.android.synthetic.main.layout_words_list.recyclerWords
import javax.inject.Inject

class WordsListFragment: BaseFragment(){
  
  override val layoutId: Int = R.layout.layout_words_list
  @Inject lateinit var viewModelFactory: ViewModelFactory
  private lateinit var viewModel: WordsListViewModel
  
  private val adapter = WordsListAdapter {
  
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    injectThis()
    viewModel = viewModelOf(viewModelFactory) {
      observe(state, ::handleWordsList)
    }
    recyclerWords.setupWith(adapter)
  }
  
  private fun injectThis() {
  
  }
  
  private fun handleWordsList(state: State) {
    when (state) {
      is State.Empty -> showToast("Empty now")
      is State.Success -> adapter.submitList(state.list)
    }
  }
  
}