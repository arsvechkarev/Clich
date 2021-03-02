package com.arsvechkarev.words.presentation

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.ClichApplication
import com.arsvechkarev.core.WordsActionsListener
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.animateGone
import com.arsvechkarev.core.extensions.animateVisible
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.core.navigator
import com.arsvechkarev.info.di.DaggerWordsListComponent
import com.arsvechkarev.words.R
import com.arsvechkarev.words.list.WordsListAdapter
import com.arsvechkarev.words.presentation.WordsListState.ShowingWords
import com.arsvechkarev.words.presentation.WordsListState.ShowingWordsForLabel
import kotlinx.android.synthetic.main.fragment_words_list.wordsFabNewWord
import kotlinx.android.synthetic.main.fragment_words_list.wordsLayoutNoWords
import kotlinx.android.synthetic.main.fragment_words_list.wordsRecycler
import kotlinx.android.synthetic.main.fragment_words_list.wordsTextLoading
import javax.inject.Inject

class WordsListFragment : BaseFragment(), WordsActionsListener {
  
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
    viewModel.state.observe(this) { handleState(it) }
    viewModel.addWordsActionsListener(this)
    viewModel.initialize(label)
  }
  
  override fun onCreated(word: Word, createdFirstWord: Boolean) {
    if (createdFirstWord) {
      wordsLayoutNoWords.animateGone()
      wordsRecycler.animateVisible()
    }
    adapter.addWord(word)
  }
  
  override fun onUpdated(word: Word) {
    adapter.updateWord(word)
  }
  
  override fun onDeleted(word: Word, deletedLastWord: Boolean) {
    if (deletedLastWord) {
      wordsLayoutNoWords.animateVisible()
      wordsRecycler.animateGone()
    }
    adapter.deleteWord(word)
  }
  
  private fun handleState(state: WordsListState) {
    when (state) {
      is ShowingWords -> handleList(state.words, R.string.text_no_words_yet)
      is ShowingWordsForLabel -> handleList(state.words, R.string.text_empty_labels)
    }
  }
  
  private fun handleList(words: List<Word>, @StringRes emptyTextRes: Int) {
    if (words.isEmpty()) {
      wordsTextLoading.setText(emptyTextRes)
      wordsLayoutNoWords.visible()
      wordsRecycler.gone()
    } else {
      adapter.submitList(words)
      wordsLayoutNoWords.gone()
      wordsRecycler.visible()
    }
  }
  
  private fun onWordClick(word: Word) {
    navigator.goToInfoFragment(word)
  }
  
  private fun setupViews() {
    wordsRecycler.layoutManager = LinearLayoutManager(context).apply {
      stackFromEnd = true
      reverseLayout = true
    }
    wordsRecycler.adapter = adapter
    wordsFabNewWord.setOnClickListener {
      navigator.goToInfoFragment(null)
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