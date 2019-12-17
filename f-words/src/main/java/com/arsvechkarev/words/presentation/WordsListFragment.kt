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
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.words
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.words.R
import com.arsvechkarev.words.di.DaggerWordsListComponent
import com.arsvechkarev.words.list.WordsListAdapter
import kotlinx.android.synthetic.main.fragment_words_list.fabNewWord
import kotlinx.android.synthetic.main.fragment_words_list.layoutStub
import kotlinx.android.synthetic.main.fragment_words_list.recyclerWords
import kotlinx.android.synthetic.main.fragment_words_list.textStub
import log.Logger.debug
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
    recyclerWords.layoutManager = LinearLayoutManager(context).apply {
      stackFromEnd = true
      reverseLayout = true
    }
    recyclerWords.adapter = adapter
    val label = arguments?.getParcelable<Label>(LABEL)
    if (label != null) {
      viewModel.getWordsOf(label).observe(this, Observer {
        handleList(it, R.string.text_empty_labels)
      })
    } else {
      viewModel.fetchWords().observe(this, Observer {
        handleList(it, R.string.text_no_words_yet)
      })
    }
    fabNewWord.setOnClickListener {
      coreActivity.goToFragmentFromRoot(InfoFragment(), InfoFragment::class, true)
    }
  }
  
  private fun handleList(it: List<Word>, @StringRes emptyTextRes: Int) {
    if (it.isEmpty()) {
      textStub.setText(emptyTextRes)
      layoutStub.visible()
      recyclerWords.gone()
    } else {
      debug { "list update: ${it.words()}" }
      adapter.submitList(it)
      layoutStub.gone()
      recyclerWords.visible()
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