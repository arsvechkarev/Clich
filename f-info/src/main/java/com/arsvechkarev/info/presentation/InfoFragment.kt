package com.arsvechkarev.info.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.di.viewmodel.ViewModelFactory
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.observe
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.info.R
import com.arsvechkarev.info.di.DaggerInfoComponent
import com.arsvechkarev.info.list.CurrentLabelsAdapter
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_info.editTextDefinition
import kotlinx.android.synthetic.main.fragment_info.editTextWord
import kotlinx.android.synthetic.main.fragment_info.imageBack
import kotlinx.android.synthetic.main.fragment_info.recyclerLabels
import kotlinx.android.synthetic.main.fragment_info.textNewWord
import javax.inject.Inject

class InfoFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.fragment_info
  @Inject lateinit var viewModelFactory: ViewModelFactory
  private lateinit var viewModel: InfoViewModel
  
  private val labelsAdapter = CurrentLabelsAdapter()
  private var previousWord: Word? = null
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    DaggerInfoComponent.create().inject(this)
    viewModel = viewModelOf(viewModelFactory)
    imageBack.setOnClickListener { popBackStack() }
    previousWord = arguments?.get(WORD_KEY) as Word?
    previousWord?.let { setWord() }
    recyclerLabels.layoutManager = FlexboxLayoutManager(context!!)
  }
  
  override fun onBackPressed() {
    saveWord()
  }
  
  private fun setWord() {
    textNewWord.gone()
    previousWord!!.let { word ->
      viewModel.getLabels(word).observe(this) { labels ->
        labelsAdapter.submitList(labels)
      }
      editTextWord.setText(word.word)
      editTextDefinition.setText(word.definition)
    }
  }
  
  private fun saveWord() {
    if (editTextWord.text.toString().isNotBlank()
      && editTextDefinition.text.toString().isNotBlank()
    ) {
      previousWord?.let {
        // Word has been passed -> updating existing word
        it.word = editTextWord.text.toString()
        it.definition = editTextDefinition.text.toString()
        viewModel.updateWord(it)
      }
      if (previousWord == null) {
        // Word hasn't been passed -> creating new word
        val newWord = Word(
          word = editTextWord.text.toString(),
          definition = editTextDefinition.text.toString()
        )
        viewModel.saveWord(newWord)
      }
    }
  }
  
  
  companion object {
    
    const val WORD_KEY = "WORD_KEY"
    
    fun of(word: Word): InfoFragment {
      val bundle = Bundle()
      bundle.putParcelable(WORD_KEY, word)
      val fragment = InfoFragment()
      fragment.arguments = bundle
      return fragment
    }
  }
}
