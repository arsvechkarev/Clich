package com.arsvechkarev.search.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.coreActivity
import com.arsvechkarev.core.di.viewmodel.ViewModelFactory
import com.arsvechkarev.core.extensions.observe
import com.arsvechkarev.core.extensions.onTextChanged
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.search.R
import com.arsvechkarev.search.di.DaggerSearchComponent
import com.arsvechkarev.words.list.WordsListAdapter
import kotlinx.android.synthetic.main.fragment_search.editTextSearchWord
import kotlinx.android.synthetic.main.fragment_search.imageBack
import kotlinx.android.synthetic.main.fragment_search.recyclerWords
import javax.inject.Inject

class SearchFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.fragment_search
  
  @Inject lateinit var viewModelFactory: ViewModelFactory
  private lateinit var viewModel: SearchViewModel
  
  private val adapter = WordsListAdapter {
    coreActivity.goToFragmentFromRoot(InfoFragment.of(it), InfoFragment::class, true)
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    DaggerSearchComponent.create().inject(this)
    viewModel = viewModelOf(viewModelFactory)
    recyclerWords.setupWith(adapter)
    editTextSearchWord.onTextChanged { text ->
      if (text.isNotBlank()) {
        viewModel.searchWords(text).observe(this@SearchFragment) { words ->
          adapter.submitList(words)
        }
      }
    }
    imageBack.setOnClickListener {
      popBackStack()
    }
  }
}
