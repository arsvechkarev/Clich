package com.arsvechkarev.words.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.coreActivity
import com.arsvechkarev.core.di.viewmodel.ViewModelFactory
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.words
import com.arsvechkarev.core.extensions.observeOnce
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.words.R
import com.arsvechkarev.words.di.DaggerWordsListComponent
import com.arsvechkarev.words.list.WordsListAdapter
import kotlinx.android.synthetic.main.fragment_words_list.fabNewWord
import kotlinx.android.synthetic.main.fragment_words_list.recyclerWords
import log.Logger.debug
import javax.inject.Inject

class WordsListFragment : BaseFragment() {
  
  private var mainList: List<Word> = ArrayList()
  override val layoutId: Int = R.layout.fragment_words_list
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
    coreActivity.subscribeOnBackStackChanges(this)
    fabNewWord.setOnClickListener {
      coreActivity.goToFragmentFromRoot(InfoFragment(), InfoFragment::class, true)
    }
  }
  
  override fun onBackStackUpdate() {
    viewModel.fetchWords()
  }
  
  private fun handleList(it: List<Word>) {
    if (it.isEmpty()) {
      debug { "got empty list" }
    } else {
      debug { "list update: ${it.words()}" }
      mainList = it
      adapter.submitList(it)
    }
  }
  
  private fun injectThis() {
    DaggerWordsListComponent.create().inject(this)
  }
  
  fun showWordsOf(label: Label) {
    debug { "showing labels" }
    viewModel.getWordsOf(label).observeOnce(this) {
      adapter.submitList(it)
    }
  }
  
  fun showMainList() {
    debug { "showing main list" }
    adapter.submitList(mainList)
  }
  
}