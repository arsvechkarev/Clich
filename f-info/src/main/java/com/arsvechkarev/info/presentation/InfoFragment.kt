package com.arsvechkarev.info.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.di.ContextModule
import com.arsvechkarev.core.di.viewmodel.ViewModelFactory
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.core.domain.model.WordEntity
import com.arsvechkarev.info.R
import com.arsvechkarev.info.di.DaggerInfoComponent
import com.arsvechkarev.storage.database.WordsDatabase
import kotlinx.android.synthetic.main.layout_info.editTextDefinition
import kotlinx.android.synthetic.main.layout_info.editTextWord
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class InfoFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.layout_info
  @Inject lateinit var viewModelFactory: ViewModelFactory
  private lateinit var viewModel: InfoViewModel
  
  private var previousWordEntity: WordEntity? = null
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    injectThis()
    viewModel = viewModelOf(viewModelFactory)
    val word = arguments?.get(WORD_KEY) as WordEntity?
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
  
  private fun setWord(wordEntity: WordEntity) {
    previousWordEntity = wordEntity
    editTextWord.setText(wordEntity.word)
    editTextDefinition.setText(wordEntity.definition)
  }
  
  private fun saveWord() {
    if (editTextWord.text.toString().isNotBlank()
      && editTextDefinition.text.toString().isNotBlank()
    ) {
      if (previousWordEntity != null) {
        previousWordEntity!!.word = editTextWord.text.toString()
        previousWordEntity!!.definition = editTextDefinition.text.toString()
        
        GlobalScope.launch {
          WordsDatabase.instance.wordDao().update(previousWordEntity!!)
        }
      } else {
        val newWord = WordEntity(
          word = editTextWord.text.toString(),
          definition = editTextDefinition.text.toString(),
          label = null
        )
        GlobalScope.launch {
          WordsDatabase.instance.wordDao().create(newWord)
        }
      }
    }
  }
  
  companion object {
    
    const val WORD_KEY = "WORD_KEY"
    
    fun of(wordEntity: WordEntity): InfoFragment {
      val bundle = Bundle()
      bundle.putParcelable(WORD_KEY, wordEntity)
      val fragment = InfoFragment()
      fragment.arguments = bundle
      return fragment
    }
  }
}
