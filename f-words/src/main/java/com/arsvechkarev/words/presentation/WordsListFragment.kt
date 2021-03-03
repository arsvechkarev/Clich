package com.arsvechkarev.words.presentation

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.ClichApplication
import com.arsvechkarev.core.datasource.WordsActionsListener
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.animateGone
import com.arsvechkarev.core.extensions.animateVisible
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.invisible
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.core.navigator
import com.arsvechkarev.info.di.DaggerWordsListComponent
import com.arsvechkarev.words.R
import com.arsvechkarev.words.list.WordsListAdapter
import com.arsvechkarev.words.presentation.WordsListState.ShowingWords
import com.arsvechkarev.words.presentation.WordsListState.ShowingWordsForLabel
import kotlinx.android.synthetic.main.fragment_words_list.wordsFabNewWord
import kotlinx.android.synthetic.main.fragment_words_list.wordsListLayout
import kotlinx.android.synthetic.main.fragment_words_list.wordsListRecycler
import kotlinx.android.synthetic.main.fragment_words_list.wordsListTextNoWords
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
    val label = arguments?.getParcelable<Label>(LABEL)
    setupViews(label == null)
    viewModel.state.observe(this, this::handleState)
    viewModel.addWordsActionsListener(this)
    viewModel.initialize(label)
  }
  
  override fun onCreatedWord(word: Word, createdFirstWord: Boolean) {
    if (createdFirstWord) {
      wordsListLayout.animateGone()
      wordsListRecycler.animateVisible()
    }
    adapter.addWord(word)
  }
  
  override fun onUpdatedWord(word: Word) {
    adapter.updateWord(word)
  }
  
  override fun onDeletedWord(word: Word, deletedLastWord: Boolean) {
    if (deletedLastWord) {
      wordsListLayout.animateVisible()
      wordsListRecycler.animateGone()
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
      wordsListTextNoWords.setText(emptyTextRes)
      wordsListLayout.visible()
      wordsListRecycler.gone()
    } else {
      adapter.changeListWithoutAnimation(words)
      wordsListLayout.gone()
      wordsListRecycler.visible()
    }
  }
  
  private fun onWordClick(word: Word) {
    navigator.goToInfoFragment(word)
  }
  
  private fun setupViews(showNewWordButton: Boolean) {
    if (showNewWordButton) {
      wordsFabNewWord.setOnClickListener { navigator.goToInfoFragment() }
    } else {
      wordsFabNewWord.invisible()
    }
    wordsListRecycler.layoutManager = LinearLayoutManager(context).apply {
      stackFromEnd = true
      reverseLayout = true
    }
    wordsListRecycler.adapter = adapter
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