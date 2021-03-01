package com.arsvechkarev.info.presentation

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.ClichApplication
import com.arsvechkarev.core.coreActivity
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.popBackStack
import com.arsvechkarev.core.extensions.showKeyboard
import com.arsvechkarev.core.extensions.string
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.info.R
import com.arsvechkarev.info.di.DaggerInfoComponent
import com.arsvechkarev.info.list.CurrentLabelsAdapter
import com.arsvechkarev.labels.list.viewholders.CheckedChangedCallback
import com.arsvechkarev.labels.presentation.LabelsCheckboxFragment
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_info.buttonAddLabels
import kotlinx.android.synthetic.main.fragment_info.editTextDefinition
import kotlinx.android.synthetic.main.fragment_info.editTextExamples
import kotlinx.android.synthetic.main.fragment_info.editTextWord
import kotlinx.android.synthetic.main.fragment_info.imageBack
import kotlinx.android.synthetic.main.fragment_info.imageMenu
import kotlinx.android.synthetic.main.fragment_info.recyclerWordsLabels
import kotlinx.android.synthetic.main.fragment_info.textNewWord
import log.Logger.debug
import org.threeten.bp.LocalDate
import javax.inject.Inject

class InfoFragment : BaseFragment() {
  
  override val layoutId: Int = R.layout.fragment_info
  
  @Inject lateinit var viewModel: InfoViewModel
  @Inject lateinit var labelsAdapter: CurrentLabelsAdapter
  
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
    DaggerInfoComponent.builder()
      .coreComponent(ClichApplication.coreComponent)
      .infoFragment(this)
      .build()
      .inject(this)
    imageBack.setOnClickListener {
      saveWord()
      popBackStack()
    }
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
    
    recyclerWordsLabels.layoutManager = flexboxLayoutManager
    recyclerWordsLabels.adapter = labelsAdapter
    buttonAddLabels.setOnClickListener {
      val fragment = if (previousWord == null) {
        LabelsCheckboxFragment.of(ArrayList(currentLabels)).also {
          it.callback = callback
        }
      } else {
        LabelsCheckboxFragment.of(previousWord!!, ArrayList(currentLabels))
      }
      coreActivity.goToFragment(fragment, LabelsCheckboxFragment::class, true)
    }
    
    imageMenu.setOnClickListener {
      val popup = PopupMenu(context!!, imageMenu)
      popup.inflate(R.menu.menu_info)
      popup.setOnMenuItemClickListener {
        if (it.itemId == R.id.itemDelete) {
          popBackStack()
          viewModel.deleteWord(previousWord!!)
          return@setOnMenuItemClickListener true
        }
        return@setOnMenuItemClickListener false
      }
      popup.show()
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
    imageMenu.visible()
    previousWord!!.let { word ->
      viewModel.getLabelsForWord(word).observe(this) { labels ->
        labelsAdapter.submitList(labels)
      }
      editTextWord.setText(word.name)
      editTextDefinition.setText(word.definition)
      editTextExamples.setText(word.examples)
    }
  }
  
  private fun saveWord() {
    debug { "Saving?" }
    if (editTextWord.text.toString().isNotBlank()) {
      previousWord?.let {
        // Word has been passed -> updating existing word
        it.name = editTextWord.string().trim()
        it.definition = editTextDefinition.string().trim()
        it.examples = editTextExamples.string().trim()
        debug { "update existing word = $it" }
        viewModel.updateWord(it)
      }
      if (previousWord == null) {
        // Word hasn't been passed -> creating a new one
        val newWord = Word(
          name = editTextWord.string().trim(),
          definition = editTextDefinition.string().trim(),
          examples = editTextExamples.string().trim(),
          creationDate = LocalDate.now().toEpochDay()
        )
        debug { "saving brand new name = $newWord" }
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
