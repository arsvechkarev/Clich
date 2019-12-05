package com.arsvechkarev.words.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.coreActivity
import com.arsvechkarev.core.di.viewmodel.ViewModelFactory
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.observe
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.showToast
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.words.R
import com.arsvechkarev.words.di.DaggerWordsListComponent
import com.arsvechkarev.words.list.WordsListAdapter
import kotlinx.android.synthetic.main.fragment_words_list.fabNewWord
import kotlinx.android.synthetic.main.fragment_words_list.recyclerWords
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
    Log.d("fiffy", "backstack")
    viewModel.fetchWords()
  }
  
  private fun handleList(it: List<Word>) {
    if (it.isEmpty()) {
      showToast("empty list")
    } else {
      Log.d("wordsing", "list update, list = $it")
      mainList = it
      adapter.submitList(it)
    }
  }
  
  private fun injectThis() {
    DaggerWordsListComponent.create().inject(this)
  }
  
  fun showWordsOf(label: Label) {
    viewModel.getWordsOf(label).observe(this) {
      adapter.submitList(it)
    }
  }
  
  fun showMainList() {
    adapter.submitList(mainList)
  }
  
}