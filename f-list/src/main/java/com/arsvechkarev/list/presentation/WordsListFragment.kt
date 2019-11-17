package com.arsvechkarev.list.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.coreActivity
import com.arsvechkarev.core.di.ContextModule
import com.arsvechkarev.core.di.viewmodel.ViewModelFactory
import com.arsvechkarev.core.extensions.observe
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.showToast
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.list.R
import com.arsvechkarev.list.di.DaggerWordsListComponent
import com.arsvechkarev.list.list.WordsListAdapter
import kotlinx.android.synthetic.main.layout_words_list.fabNewWord
import kotlinx.android.synthetic.main.layout_words_list.recyclerWords
import javax.inject.Inject

class WordsListFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.layout_words_list
  @Inject lateinit var viewModelFactory: ViewModelFactory
  private lateinit var viewModel: WordsListViewModel
  
  private val adapter = WordsListAdapter {
    coreActivity.goToFragmentFromRoot(InfoFragment.of(it), InfoFragment::class, true)
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    injectThis()
    viewModel = viewModelOf(viewModelFactory) {
      observe(state, ::handleWordsList)
    }
    recyclerWords.setupWith(adapter)
    viewModel.fetchWords()
    fabNewWord.setOnClickListener {
      coreActivity.goToFragmentFromRoot(InfoFragment(), InfoFragment::class, true)
    }
  }
  
  override fun onBackPressed() {
    Log.d("qwerty", "list : pressed")
    showToast("words list on back pressed")
  }
  
  private fun injectThis() {
    DaggerWordsListComponent.builder()
      .contextModule(ContextModule(context!!))
      .build()
      .inject(this)
  }
  
  private fun handleWordsList(state: State) {
    when (state) {
      is State.Empty -> showToast("Empty now")
      is State.Success -> adapter.submitList(state.list)
    }
  }
  
}