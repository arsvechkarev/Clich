package com.arsvechkarev.search.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.ClichApplication
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.hideKeyboard
import com.arsvechkarev.core.extensions.invisible
import com.arsvechkarev.core.extensions.onTextChanged
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.core.navigator
import com.arsvechkarev.info.di.DaggerSearchComponent
import com.arsvechkarev.search.R
import com.arsvechkarev.search.list.SearchAdapter
import com.arsvechkarev.search.presentation.SearchState.DisplayingAllWords
import com.arsvechkarev.search.presentation.SearchState.FoundWords
import com.arsvechkarev.search.presentation.SearchState.NoWordsFound
import kotlinx.android.synthetic.main.fragment_search.imageBack
import kotlinx.android.synthetic.main.fragment_search.layoutNoWordsFound
import kotlinx.android.synthetic.main.fragment_search.recyclerFoundWords
import kotlinx.android.synthetic.main.fragment_search.searchEditText
import javax.inject.Inject

class SearchFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.fragment_search
  
  @Inject lateinit var viewModel: SearchViewModel
  @Inject lateinit var adapter: SearchAdapter
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    DaggerSearchComponent.builder()
      .coreComponent(ClichApplication.coreComponent)
      .onWordClick { word -> onWordClicked(word) }
      .searchFragment(this)
      .build()
      .inject(this)
    viewModel.state.observe(this) { searchState -> handleState(searchState) }
    viewModel.fetchAllWords()
    setup()
  }
  
  private fun handleState(searchState: SearchState) {
    when (searchState) {
      is DisplayingAllWords -> {
        adapter.submitList(searchState.words)
      }
      is NoWordsFound -> {
        layoutNoWordsFound.visible()
        recyclerFoundWords.invisible()
      }
      is FoundWords -> {
        layoutNoWordsFound.invisible()
        recyclerFoundWords.visible()
        adapter.submitList(searchState.words, searchState.searchedText)
      }
    }
  }
  
  private fun onWordClicked(word: Word) {
    hideKeyboard()
    navigator.goToInfoFragment(word)
  }
  
  private fun setup() {
    searchEditText.onTextChanged { text -> viewModel.onSearchTextEntered(text) }
    val linearLayoutManager = LinearLayoutManager(recyclerFoundWords.context).apply {
      reverseLayout = true
      stackFromEnd = true
    }
    recyclerFoundWords.layoutManager = linearLayoutManager
    recyclerFoundWords.adapter = adapter
    imageBack.setOnClickListener {
      hideKeyboard()
      popBackStack()
    }
  }
}