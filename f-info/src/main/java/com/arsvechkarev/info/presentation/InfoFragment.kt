package com.arsvechkarev.info.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.di.ContextModule
import com.arsvechkarev.core.di.viewmodel.ViewModelFactory
import com.arsvechkarev.core.extensions.showToast
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.info.R
import com.arsvechkarev.info.di.DaggerInfoComponent
import com.arsvechkarev.core.WordsDatabase
import kotlinx.android.synthetic.main.layout_info.editTextDefinition
import kotlinx.android.synthetic.main.layout_info.editTextWord
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
    Log.d("zxcvbn", "on back pressed")
    saveWord()
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
  
  private fun saveWord() {
    if (editTextWord.text.toString().isNotBlank()
      && editTextDefinition.text.toString().isNotBlank()
    ) {
      if (previousWord != null) {
        val newWord = Word(
          previousWord!!.id,
          editTextWord.text.toString(),
          editTextDefinition.text.toString(),
          null
        )
        GlobalScope.launch {
  
          WordsDatabase.getInstance().wordDao().update(newWord)
        }
      } else {
        val newWord = Word(
          word = editTextWord.text.toString(),
          definition = editTextDefinition.text.toString(),
          label = null
        )
        GlobalScope.launch {
          WordsDatabase.getInstance().wordDao().create(newWord)
        }
      }
      
      
//      previousWord?.let { viewModel.deleteWord(it) }
//      val word = Word(
//        editTextWord.text.toString(),
//        editTextDefinition.text.toString(),
//        null
//      )
//      viewModel.saveWord(word)
      showToast("word saved ffsddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd")
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
