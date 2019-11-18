package com.arsvechkarev.info

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.domain.model.WordEntity
import com.arsvechkarev.core.extensions.inBackground
import com.arsvechkarev.storage.database.WordsDatabase
import kotlinx.android.synthetic.main.layout_info.editTextDefinition
import kotlinx.android.synthetic.main.layout_info.editTextWord

class InfoFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.layout_info
  
  private var previousWordEntity: WordEntity? = null
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    previousWordEntity = arguments?.get(WORD_KEY) as WordEntity?
    if (previousWordEntity != null) {
      setWord()
    }
  }
  
  override fun onBackPressed() {
    saveWord()
  }
  
  private fun setWord() {
    previousWordEntity!!.let {
      editTextWord.setText(it.word)
      editTextDefinition.setText(it.definition)
    }
  }
  
  private fun saveWord() {
    if (editTextWord.text.toString().isNotBlank()
      && editTextDefinition.text.toString().isNotBlank()
    ) {
      previousWordEntity?.let {
        // Word has been passed i.e editing existing word
        it.word = editTextWord.text.toString()
        it.definition = editTextDefinition.text.toString()
        inBackground {
          WordsDatabase.instance.update(previousWordEntity!!)
        }
      }
      if (previousWordEntity == null) {
        // Word hasn't been passed -> creating new word
        val newWord = WordEntity(
          word = editTextWord.text.toString(),
          definition = editTextDefinition.text.toString(),
          label = null
        )
        inBackground { WordsDatabase.instance.create(newWord) }
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
