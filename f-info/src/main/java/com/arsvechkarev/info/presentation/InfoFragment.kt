package com.arsvechkarev.info.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.di.ContextModule
import com.arsvechkarev.core.di.viewmodel.ViewModelFactory
import com.arsvechkarev.core.extensions.showToast
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.core.model.Word
import com.arsvechkarev.info.R
import com.arsvechkarev.info.di.DaggerInfoComponent
import com.arsvechkarev.storage.di.StorageModule
import kotlinx.android.synthetic.main.layout_info.editTextDefinition
import kotlinx.android.synthetic.main.layout_info.editTextWord
import javax.inject.Inject

class InfoFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.layout_info
  @Inject lateinit var viewModelFactory: ViewModelFactory
  private lateinit var viewModel: InfoViewModel
  
  private var previousWord: Word? = null
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    injectThis()
    viewModel = viewModelOf(viewModelFactory)
    val word = arguments?.get(WORD_KEY) as Word?
    if (word != null) {
      setWord(word)
    }
  }
  
  override fun onBackPressed() {
    Log.d("qwerty", "info : pressed")
    showToast("info on back pressed")
  }
  
  private fun injectThis() {
    DaggerInfoComponent.builder()
      .contextModule(ContextModule(context!!))
      .build()
      .inject(this)
  }
  
  private fun setWord(word: Word) {
    previousWord = word
    editTextWord.setText(word.word)
    editTextDefinition.setText(word.definition)
  }
  
  override fun onStop() {
    previousWord?.let { viewModel.deleteWord(it) }
    val word = Word(
      editTextWord.text.toString(),
      editTextDefinition.text.toString(),
      null
    )
    viewModel.saveWord(word)
    super.onStop()
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
