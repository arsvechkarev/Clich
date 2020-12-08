package com.arsvechkarev.words.presentation

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.coreActivity
import com.arsvechkarev.core.di.viewmodel.ViewModelFactory
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.core.recyler.DisplayableItem
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.words.R
import com.arsvechkarev.words.di.DaggerWordsListComponent
import com.arsvechkarev.words.list.WordsListAdapter
import kotlinx.android.synthetic.main.fragment_words_list.wordsFabNewWord
import kotlinx.android.synthetic.main.fragment_words_list.wordsLayoutLoading
import kotlinx.android.synthetic.main.fragment_words_list.wordsRecycler
import kotlinx.android.synthetic.main.fragment_words_list.wordsTextLoading
import javax.inject.Inject

class WordsListFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.fragment_words_list
  
  @Inject lateinit var viewModelFactory: ViewModelFactory
  
  private lateinit var viewModel: WordsListViewModel
  
  private val adapter = WordsListAdapter {
    coreActivity.goToFragmentFromRoot(InfoFragment.of(it), InfoFragment::class, true)
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    DaggerWordsListComponent.create().inject(this)
    viewModel = viewModelOf(viewModelFactory)
    wordsRecycler.layoutManager = LinearLayoutManager(context).apply {
      stackFromEnd = true
      reverseLayout = true
    }
    wordsRecycler.adapter = adapter
    val label = arguments?.getParcelable<Label>(LABEL)
    if (label != null) {
      viewModel.getWordsFor(label).observe(this) {
        handleList(it, R.string.text_empty_labels)
      }
    } else {
      viewModel.fetchAll().observe(this, Observer {
        handleList(it, R.string.text_no_words_yet)
      })
    }
    wordsFabNewWord.setOnClickListener {
      coreActivity.goToFragmentFromRoot(InfoFragment(), InfoFragment::class, true)
    }
  }
  
  private fun handleList(it: List<DisplayableItem>, @StringRes emptyTextRes: Int) {
    if (it.isEmpty()) {
      wordsTextLoading.setText(emptyTextRes)
      wordsLayoutLoading.visible()
      wordsRecycler.gone()
    } else {
      adapter.submitList(it)
      wordsLayoutLoading.gone()
      wordsRecycler.visible()
    }
  }
  
  companion object {
    
    const val LABEL = "LABEL"
    
    fun of(label: Label): WordsListFragment {
      val bundle = Bundle()
      bundle.putParcelable(LABEL, label)
      val fragment = WordsListFragment()
      fragment.arguments = bundle
      return fragment
    }
  }
}