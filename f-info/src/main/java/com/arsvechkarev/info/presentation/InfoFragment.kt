package com.arsvechkarev.info.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.toWordEntity
import com.arsvechkarev.core.extensions.inBackground
import com.arsvechkarev.info.R
import com.arsvechkarev.storage.database.WordsDatabase
import kotlinx.android.synthetic.main.layout_info.editTextDefinition
import kotlinx.android.synthetic.main.layout_info.editTextWord

class InfoFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.layout_info
  
  private var previousWord: Word? = null
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    previousWord = arguments?.get(WORD_KEY) as Word?
    previousWord?.let { setWord() }
  }
  
  override fun onBackPressed() {
    saveWord()
  }
  
  private fun setWord() {
    previousWord!!.let {
      editTextWord.setText(it.word)
      editTextDefinition.setText(it.definition)
    }
  }
  
  private fun saveWord() {
    if (editTextWord.text.toString().isNotBlank()
      && editTextDefinition.text.toString().isNotBlank()
    ) {
      previousWord?.let {
        // Word has been passed i.e editing existing word
        it.word = editTextWord.text.toString()
        it.definition = editTextDefinition.text.toString()
        inBackground { WordsDatabase.instance.update(previousWord!!.toWordEntity()) }
      }
      if (previousWord == null) {
        // Word hasn't been passed -> creating new word
        val newWord = Word(
          word = editTextWord.text.toString(),
          definition = editTextDefinition.text.toString(),
          label = null
        )
        inBackground { WordsDatabase.instance.create(newWord.toWordEntity()) }
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
