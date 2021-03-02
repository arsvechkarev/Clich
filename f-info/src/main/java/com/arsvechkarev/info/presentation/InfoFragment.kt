package com.arsvechkarev.info.presentation

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.CheckedChangedCallback
import com.arsvechkarev.core.ClichApplication
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.string
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.core.navigator
import com.arsvechkarev.info.R
import com.arsvechkarev.info.di.DaggerInfoComponent
import com.arsvechkarev.info.list.LabelsForWordAdapter
import com.arsvechkarev.info.presentation.InfoState.ExistingWordState
import com.arsvechkarev.info.presentation.InfoState.NewWordState
import com.arsvechkarev.info.presentation.InfoState.OnAddLabelsClicked
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
  
  @Inject lateinit var viewModel: InfoViewModel
  @Inject lateinit var labelsForWordAdapter: LabelsForWordAdapter
  
  private val callback = object : CheckedChangedCallback {
    
    override fun onLabelSelected(label: Label) {
      viewModel.addLabel(label)
    }
    
    override fun onLabelUnselected(label: Label) {
      viewModel.deleteLabel(label)
    }
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    DaggerInfoComponent.builder()
      .coreComponent(ClichApplication.coreComponent)
      .infoFragment(this)
      .build()
      .inject(this)
    viewModel.initialize(arguments?.get(WORD_KEY) as? Word)
    viewModel.state.observe(this) { state -> handleState(state) }
    viewModel.fetchLabelsForWord()?.observe(this) { labels -> handleLabels(labels) }
    setupViews()
  }
  
  override fun onBackPressed(): Boolean {
    saveWord()
    return super.onBackPressed()
  }
  
  private fun handleState(state: InfoState) {
    when (state) {
      is ExistingWordState -> {
        setExistingWord(state.word)
      }
      NewWordState -> {
      
      }
      is OnAddLabelsClicked -> {
        navigator.goToLabelsCheckboxFragment(state.word)
      }
    }
  }
  
  private fun handleLabels(labels: List<Label>) {
    labelsForWordAdapter.changeListWithoutAnimation(labels)
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
  
  private fun saveWord() {
    val name = editTextWord.string().trim()
    val definition = editTextDefinition.string().trim()
    val examples = editTextExamples.string().trim()
    viewModel.saveData(name, definition, examples)
  }
  
  private fun setupViews() {
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
      viewModel.onAddLabelsClicked()
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