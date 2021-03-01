package com.arsvechkarev.search.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.ClichApplication
import com.arsvechkarev.core.coreActivity
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.hideKeyboard
import com.arsvechkarev.core.extensions.invisible
import com.arsvechkarev.core.extensions.observeOnce
import com.arsvechkarev.core.extensions.onTextChanged
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.string
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.info.di.DaggerSearchComponent
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.search.R
import com.arsvechkarev.search.labels.WordsListAdapter
import kotlinx.android.synthetic.main.fragment_search.imageBack
import kotlinx.android.synthetic.main.fragment_search.layoutNoWordsFound
import kotlinx.android.synthetic.main.fragment_search.recyclerFoundWords
import kotlinx.android.synthetic.main.fragment_search.searchEditText
import javax.inject.Inject

class SearchFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.fragment_search
  
  private var currentList: List<Word>? = null
  private var allList: List<Word>? = null
  
  @Inject lateinit var viewModel: SearchViewModel
  @Inject lateinit var adapter: WordsListAdapter
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    DaggerSearchComponent.builder()
      .coreComponent(ClichApplication.coreComponent)
      .onWordClick { word -> onWordClicked(word) }
      .searchFragment(this)
      .build()
      .inject(this)
    recyclerFoundWords.setupWith(adapter)
    viewModel.getAllWords().observeOnce(this) {
      if (allList != it) {
        allList = it
        adapter.submitList(allList)
      }
    }
    searchEditText.onTextChanged { text ->
      if (text.isNotBlank()) {
        viewModel.searchWords(text).observe(this@SearchFragment) { words ->
          if (words.isEmpty()) {
            layoutNoWordsFound.visible()
            recyclerFoundWords.invisible()
          } else {
            currentList = words
            adapter.submitList(currentList, text)
            layoutNoWordsFound.invisible()
            recyclerFoundWords.visible()
          }
        }
      } else {
        adapter.submitList(allList)
      }
    }
    imageBack.setOnClickListener {
      hideKeyboard(searchEditText)
      popBackStack()
    }
    coreActivity.subscribeOnBackStackChanges(this)
  }
  
  override fun onBackStackUpdate() {
    if (searchEditText != null) {
      adapter.submitList(currentList, searchEditText.string())
    }
  }
  
  private fun onWordClicked(word: Word) {
    hideKeyboard(searchEditText)
    coreActivity.goToFragment(InfoFragment.of(word), InfoFragment::class, true)
  }
}