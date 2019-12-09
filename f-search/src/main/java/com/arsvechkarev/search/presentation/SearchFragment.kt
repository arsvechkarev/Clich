package com.arsvechkarev.search.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.coreActivity
import com.arsvechkarev.core.di.viewmodel.ViewModelFactory
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.hideKeyboard
import com.arsvechkarev.core.extensions.observe
import com.arsvechkarev.core.extensions.observeOnce
import com.arsvechkarev.core.extensions.onTextChanged
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.showKeyboard
import com.arsvechkarev.core.extensions.string
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.search.R
import com.arsvechkarev.search.di.DaggerSearchComponent
import com.arsvechkarev.search.labels.WordsListAdapter
import kotlinx.android.synthetic.main.fragment_search.imageBack
import kotlinx.android.synthetic.main.fragment_search.layoutStub
import kotlinx.android.synthetic.main.fragment_search.recyclerFoundWords
import kotlinx.android.synthetic.main.fragment_search.searchEditText
import javax.inject.Inject

class SearchFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.fragment_search
  
  @Inject lateinit var viewModelFactory: ViewModelFactory
  private lateinit var viewModel: SearchViewModel
  private var currentList: List<Word>? = null
  private var allList: List<Word>? = null
  
  private val adapter = WordsListAdapter {
    hideKeyboard(searchEditText)
    coreActivity.goToFragmentFromRoot(InfoFragment.of(it), InfoFragment::class, true)
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    DaggerSearchComponent.create().inject(this)
    viewModel = viewModelOf(viewModelFactory)
    recyclerFoundWords.setupWith(adapter)
    viewModel.getAllWords().observeOnce(this, Observer {
      if (allList != it) {
        allList = it
        adapter.submitList(allList)
      }
    })
    searchEditText.onTextChanged { text ->
      if (text.isNotBlank()) {
        viewModel.searchWords(text).observe(this@SearchFragment) { words ->
          if (words.isEmpty()) {
            layoutStub.visible()
            recyclerFoundWords.gone()
          } else {
            currentList = words
            adapter.submitList(currentList, text)
            layoutStub.gone()
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
  
  override fun onResume() {
    super.onResume()
    searchEditText.requestFocus()
    showKeyboard()
  }
}
