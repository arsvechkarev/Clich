package com.arsvechkarev.words.presentation

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.ClichApplication
import com.arsvechkarev.core.coreActivity
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.info.di.DaggerWordsListComponent
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.words.R
import com.arsvechkarev.words.list.WordsListAdapter
import kotlinx.android.synthetic.main.fragment_words_list.wordsFabNewWord
import kotlinx.android.synthetic.main.fragment_words_list.wordsLayoutLoading
import kotlinx.android.synthetic.main.fragment_words_list.wordsRecycler
import kotlinx.android.synthetic.main.fragment_words_list.wordsTextLoading
import javax.inject.Inject

class WordsListFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.fragment_words_list
  
  @Inject lateinit var viewModel: WordsListViewModel
  @Inject lateinit var adapter: WordsListAdapter
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    DaggerWordsListComponent.builder()
      .coreComponent(ClichApplication.coreComponent)
      .wordsListFragment(this)
      .onWordClick { word -> onWordClick(word) }
      .build()
      .inject(this)
    setupViews()
    val label = arguments?.getParcelable<Label>(LABEL)
    if (label != null) {
      viewModel.getWordsFor(label).observe(this) { words ->
        handleList(words, R.string.text_empty_labels)
      }
    } else {
      viewModel.fetchAll().observe(this, { words ->
        handleList(words, R.string.text_no_words_yet)
      })
    }
  }
  
  private fun handleList(words: List<Word>, @StringRes emptyTextRes: Int) {
    if (words.isEmpty()) {
      wordsTextLoading.setText(emptyTextRes)
      wordsLayoutLoading.visible()
      wordsRecycler.gone()
    } else {
      adapter.submitList(words)
      wordsLayoutLoading.gone()
      wordsRecycler.visible()
    }
  }
  
  private fun onWordClick(word: Word) {
    coreActivity.goToFragment(InfoFragment.of(word), InfoFragment::class, true)
  }
  
  private fun setupViews() {
    wordsRecycler.layoutManager = LinearLayoutManager(context).apply {
      stackFromEnd = true
      reverseLayout = true
    }
    wordsRecycler.adapter = adapter
    wordsFabNewWord.setOnClickListener {
      coreActivity.goToFragment(InfoFragment(), InfoFragment::class, true)
    }
  }
  
  companion object {
    
    const val LABEL = "LABEL"
    
    fun of(label: Label) = WordsListFragment().apply {
      arguments = Bundle().apply {
        putParcelable(LABEL, label)
      }
    }
  }
}