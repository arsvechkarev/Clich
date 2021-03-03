package com.arsvechkarev.info.presentation

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.showKeyboard
import com.arsvechkarev.core.extensions.string
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.core.navigator
import com.arsvechkarev.featurecommon.WordInfoComponentHolder
import com.arsvechkarev.featurecommon.WordInfoComponentHolder.wordInfoComponent
import com.arsvechkarev.featurecommon.WordInfoViewModel
import com.arsvechkarev.info.R
import com.arsvechkarev.info.di.DaggerInfoComponent
import com.arsvechkarev.info.list.LabelsForWordAdapter
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_info.buttonAddLabels
import kotlinx.android.synthetic.main.fragment_info.editTextDefinition
import kotlinx.android.synthetic.main.fragment_info.editTextExamples
import kotlinx.android.synthetic.main.fragment_info.editTextWord
import kotlinx.android.synthetic.main.fragment_info.imageBack
import kotlinx.android.synthetic.main.fragment_info.imageMenu
import kotlinx.android.synthetic.main.fragment_info.recyclerWordsLabels
import kotlinx.android.synthetic.main.fragment_info.textNewWord
import javax.inject.Inject

class InfoFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.fragment_info
  
  @Inject lateinit var viewModel: WordInfoViewModel
  @Inject lateinit var labelsForWordAdapter: LabelsForWordAdapter
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    WordInfoComponentHolder.create(this)
    DaggerInfoComponent.builder()
      .wordsInfoComponent(wordInfoComponent)
      .build()
      .inject(this)
    viewModel.alreadyExistingWord = arguments?.get(WORD_KEY) as? Word
    viewModel.labelsForWord.observe(this, labelsForWordAdapter::changeListWithoutAnimation)
    viewModel.fetchLabelsForWordIfAny()
    setupViews()
  }
  
  override fun onBackPressed(): Boolean {
    saveWord()
    return super.onBackPressed()
  }
  
  override fun onDestroyView() {
    super.onDestroyView()
    WordInfoComponentHolder.clear()
  }
  
  private fun saveWord() {
    val name = editTextWord.string().trim()
    val definition = editTextDefinition.string().trim()
    val examples = editTextExamples.string().trim()
    viewModel.saveData(name, definition, examples)
  }
  
  private fun setupViews() {
    val word = viewModel.alreadyExistingWord
    if (word != null) {
      setExistingWord(word)
    } else {
      editTextWord.requestFocus()
      showKeyboard()
    }
    imageBack.setOnClickListener {
      saveWord()
      popBackStack()
    }
    val flexboxLayoutManager = object : FlexboxLayoutManager(context!!) {
      override fun canScrollVertically(): Boolean {
        return false
      }
    }
    recyclerWordsLabels.layoutManager = flexboxLayoutManager
    recyclerWordsLabels.adapter = labelsForWordAdapter
    buttonAddLabels.setOnClickListener {
      navigator.goToLabelsCheckboxFragment(word)
    }
  }
  
  private fun setExistingWord(word: Word) {
    textNewWord.gone()
    imageMenu.visible()
    editTextWord.setText(word.name)
    editTextDefinition.setText(word.definition)
    editTextExamples.setText(word.examples)
    imageMenu.setOnClickListener {
      val popup = PopupMenu(context!!, imageMenu)
      popup.inflate(R.menu.menu_info)
      popup.setOnMenuItemClickListener {
        if (it.itemId == R.id.itemDelete) {
          popBackStack()
          viewModel.deleteWord()
          return@setOnMenuItemClickListener true
        }
        return@setOnMenuItemClickListener false
      }
      popup.show()
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