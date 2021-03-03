package com.arsvechkarev.words.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.ClichApplication
import com.arsvechkarev.core.WordsActionsListener
import com.arsvechkarev.core.applyAllWordsListStyle
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.animateGone
import com.arsvechkarev.core.extensions.animateVisible
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.core.navigator
import com.arsvechkarev.info.di.DaggerWordsComponent
import com.arsvechkarev.words.R
import com.arsvechkarev.words.list.WordsListAdapter
import kotlinx.android.synthetic.main.fragment_words_list.wordsFabNewWord
import kotlinx.android.synthetic.main.fragment_words_list.wordsListLayoutNoWords
import kotlinx.android.synthetic.main.fragment_words_list.wordsListRecycler
import kotlinx.android.synthetic.main.fragment_words_list.wordsListTextNoWords
import javax.inject.Inject

class WordsListFragment : BaseFragment(), WordsActionsListener {
  
  override val layoutId: Int = R.layout.fragment_words_list
  
  @Inject lateinit var viewModel: WordsListViewModel
  @Inject lateinit var adapter: WordsListAdapter
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    DaggerWordsComponent.builder()
      .coreComponent(ClichApplication.coreComponent)
      .fragment(this)
      .onWordClick(navigator::goToInfoFragment)
      .build()
      .inject(this)
    setupViews()
    viewModel.state.observe(this, this::handleWords)
    viewModel.addWordsActionsListener(this)
    viewModel.fetchAll()
  }
  
  override fun onCreatedWord(word: Word, createdFirstWord: Boolean) {
    if (createdFirstWord) {
      wordsListLayoutNoWords.animateGone()
      wordsListRecycler.animateVisible()
    }
    adapter.addWord(word)
  }
  
  override fun onUpdatedWord(word: Word) {
    adapter.updateWord(word)
  }
  
  override fun onDeletedWord(word: Word, deletedLastWord: Boolean) {
    if (deletedLastWord) {
      wordsListLayoutNoWords.animateVisible()
      wordsListRecycler.animateGone()
    }
    adapter.deleteWord(word)
  }
  
  private fun handleWords(words: List<Word>) {
    if (words.isNotEmpty()) {
      adapter.changeListWithoutAnimation(words)
      wordsListLayoutNoWords.gone()
      wordsListRecycler.visible()
    } else {
      wordsListTextNoWords.setText(R.string.text_no_words_yet)
      wordsListLayoutNoWords.visible()
      wordsListRecycler.gone()
    }
  }
  
  private fun setupViews() {
    wordsFabNewWord.setOnClickListener { navigator.goToInfoFragment() }
    wordsListRecycler.applyAllWordsListStyle(adapter)
  }
}