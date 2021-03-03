package com.arsvechkarev.words.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.ClichApplication
import com.arsvechkarev.core.applyAllWordsListStyle
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.animateGone
import com.arsvechkarev.core.extensions.animateVisible
import com.arsvechkarev.core.extensions.invisible
import com.arsvechkarev.core.navigator
import com.arsvechkarev.info.di.DaggerWordsComponent
import com.arsvechkarev.words.R
import com.arsvechkarev.words.list.WordsListAdapter
import kotlinx.android.synthetic.main.fragment_words_list.wordsFabNewWord
import kotlinx.android.synthetic.main.fragment_words_list.wordsListLayoutNoWords
import kotlinx.android.synthetic.main.fragment_words_list.wordsListRecycler
import kotlinx.android.synthetic.main.fragment_words_list.wordsListTextNoWords
import javax.inject.Inject

class WordsForLabelFragment : BaseFragment() {
  
  override val layoutId = R.layout.fragment_words_list
  
  @Inject lateinit var viewModel: WordsForLabelViewModel
  @Inject lateinit var adapter: WordsListAdapter
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    DaggerWordsComponent.builder()
      .coreComponent(ClichApplication.coreComponent)
      .fragment(this)
      .onWordClick(navigator::goToInfoFragment)
      .build()
      .inject(this)
    val label = arguments!!.get(LABEL) as Label
    setupViews()
    viewModel.fetchWordsForLabelLive(label).observe(this) { words ->
      handleWordsForLabel(words)
    }
  }
  
  private fun handleWordsForLabel(words: List<Word>) {
    if (words.isEmpty()) {
      wordsListTextNoWords.setText(R.string.text_empty_labels)
      wordsListLayoutNoWords.animateVisible()
      wordsListRecycler.animateGone()
    } else {
      adapter.changeListWithoutAnimation(words)
      wordsListLayoutNoWords.animateGone()
      wordsListRecycler.animateVisible()
    }
  }
  
  private fun setupViews() {
    wordsFabNewWord.invisible()
    wordsListRecycler.applyAllWordsListStyle(adapter)
  }
  
  companion object {
    
    private const val LABEL = "LABEL"
    
    fun of(label: Label) = WordsForLabelFragment().apply {
      arguments = Bundle().apply {
        putParcelable(LABEL, label)
      }
    }
  }
}