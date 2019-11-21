package com.arsvechkarev.words.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.coreActivity
import com.arsvechkarev.core.di.viewmodel.ViewModelFactory
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.showToast
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.words.R
import com.arsvechkarev.words.di.DaggerWordsListComponent
import com.arsvechkarev.words.list.WordsListAdapter
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
    viewModel = viewModelOf(viewModelFactory)
    recyclerWords.setupWith(adapter)
    viewModel.fetchWords().observe(this, Observer(::handleList))
    fabNewWord.setOnClickListener {
      coreActivity.goToFragmentFromRoot(InfoFragment(), InfoFragment::class, true)
    }
  }
  
  private fun handleList(it: List<Word>) {
    if (it.isEmpty()) {
      showToast("empty list")
    } else {
      adapter.submitList(it)
    }
  }
  
  private fun injectThis() {
    DaggerWordsListComponent.create().inject(this)
  }
  
}