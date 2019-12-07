package com.arsvechkarev.info.presentation

import android.os.Bundle
import android.view.View
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.coreActivity
import com.arsvechkarev.core.di.viewmodel.ViewModelFactory
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.observe
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.showKeyboard
import com.arsvechkarev.core.extensions.viewModelOf
import com.arsvechkarev.info.R
import com.arsvechkarev.info.di.DaggerInfoComponent
import com.arsvechkarev.info.list.CurrentLabelsAdapter
import com.arsvechkarev.labels.list.viewholders.CheckedChangedCallback
import com.arsvechkarev.labels.presentation.LabelsCheckboxFragment
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_info.buttonAddLabel
import kotlinx.android.synthetic.main.fragment_info.editTextDefinition
import kotlinx.android.synthetic.main.fragment_info.editTextWord
import kotlinx.android.synthetic.main.fragment_info.imageBack
import kotlinx.android.synthetic.main.fragment_info.recyclerLabels
import kotlinx.android.synthetic.main.fragment_info.textNewWord
import log.Logger.debug
import javax.inject.Inject

class InfoFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.fragment_info
  
  @Inject lateinit var viewModelFactory: ViewModelFactory
  private lateinit var viewModel: InfoViewModel
  private val labelsAdapter = CurrentLabelsAdapter()
  
  private var previousWord: Word? = null
  private var currentLabels: MutableList<Label> = ArrayList()
  
  private val callback = object : CheckedChangedCallback {
    override fun onLabelSelected(label: Label) {
      currentLabels.add(label)
      labelsAdapter.submitList(currentLabels)
    }
    
    override fun onLabelUnselected(label: Label) {
      currentLabels.remove(label)
      labelsAdapter.submitList(currentLabels)
    }
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    DaggerInfoComponent.create().inject(this)
    viewModel = viewModelOf(viewModelFactory)
    imageBack.setOnClickListener { popBackStack() }
    previousWord = arguments?.get(WORD_KEY) as Word?
    
    if (previousWord != null) {
      previousWord?.let { setWord() }
      handleLabels()
    }
    
    val flexboxLayoutManager = object : FlexboxLayoutManager(context!!) {
      override fun canScrollVertically(): Boolean {
        return false
      }
    }
    
    recyclerLabels.layoutManager = flexboxLayoutManager
    recyclerLabels.adapter = labelsAdapter
    buttonAddLabel.setOnClickListener {
      val fragment = if (previousWord == null) {
        LabelsCheckboxFragment.of(ArrayList(currentLabels)).also {
          it.callback = callback
        }
      } else {
        LabelsCheckboxFragment.of(previousWord!!, ArrayList(currentLabels))
      }
      coreActivity.goToFragmentFromRoot(fragment, LabelsCheckboxFragment::class, true)
    }
  }
  
  override fun onResume() {
    super.onResume()
    if (previousWord == null) {
      editTextWord.requestFocus()
      showKeyboard()
    }
  }
  
  override fun onBackPressed(): Boolean {
    saveWord()
    return super.onBackPressed()
  }
  
  private fun handleLabels() {
    viewModel.getLabelsForWord(previousWord!!).observe(this@InfoFragment) { labels ->
      currentLabels = labels.toMutableList()
      labelsAdapter.submitList(currentLabels)
    }
  }
  
  private fun setWord() {
    textNewWord.gone()
    previousWord!!.let { word ->
      viewModel.getLabelsForWord(word).observe(this) { labels ->
        labelsAdapter.submitList(labels)
      }
      editTextWord.setText(word.word)
      editTextDefinition.setText(word.definition ?: "")
    }
  }
  
  private fun saveWord() {
    debug { "saving?" }
    if (editTextWord.text.toString().isNotBlank()) {
      previousWord?.let {
        // Word has been passed -> updating existing word
        it.word = editTextWord.text.toString()
        val definitionText = editTextDefinition.text.toString()
        it.definition = if (definitionText.isNotBlank()) definitionText else null
        debug { "update existing word = $it" }
        viewModel.updateWord(it)
      }
      if (previousWord == null) {
        // Word hasn't been passed -> creating new word
        val newWord = Word(
          word = editTextWord.text.toString(),
          definition = editTextDefinition.text.toString()
        )
        debug { "saving brand new word = $newWord" }
        viewModel.saveWordWithLabels(newWord, currentLabels)
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
