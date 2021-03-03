package com.arsvechkarev.clich.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.fragment.app.Fragment
import com.arsvechkarev.clich.R
import com.arsvechkarev.clich.di.DaggerMainComponent
import com.arsvechkarev.clich.presentation.MainScreenState.LoadedLabels
import com.arsvechkarev.clich.presentation.MainScreenState.NoLabels
import com.arsvechkarev.core.ClichApplication
import com.arsvechkarev.core.Navigator
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.extensions.Operation.ADD
import com.arsvechkarev.core.extensions.Operation.REPLACE
import com.arsvechkarev.core.extensions.close
import com.arsvechkarev.core.extensions.findFragment
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.isOpened
import com.arsvechkarev.core.extensions.setupToggle
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.switchToFragment
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.presentation.LabelsCheckboxFragment
import com.arsvechkarev.labels.presentation.LabelsFragment
import com.arsvechkarev.search.presentation.SearchFragment
import com.arsvechkarev.words.presentation.WordsForLabelFragment
import com.arsvechkarev.words.presentation.WordsListFragment
import kotlinx.android.synthetic.main.activity_main.layoutDrawer
import kotlinx.android.synthetic.main.activity_main.textLabelName
import kotlinx.android.synthetic.main.activity_main.textSearchWord
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.partial_layout_drawer.drawerButtonAddLabels
import kotlinx.android.synthetic.main.partial_layout_drawer.drawerButtonEditLabels
import kotlinx.android.synthetic.main.partial_layout_drawer.layoutLabelsDrawerStub
import kotlinx.android.synthetic.main.partial_layout_drawer.recyclerDrawerLabels
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Navigator {
  
  @Inject lateinit var viewModel: MainViewModel
  @Inject lateinit var labelsAdapter: LabelsAdapter
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerMainComponent.builder()
      .coreComponent(ClichApplication.coreComponent)
      .mainActivity(this)
      .onLabelClick { label -> onAdapterClick(label) }
      .build()
      .inject(this)
    performSetup()
    viewModel.loadLabels().observe(this, ::handleState)
    switchToFragment(R.id.baseContainer, WordsListFragment(), REPLACE)
  }
  
  override fun goToLabelsCheckboxFragment(word: Word?) {
    switchToFragment(R.id.layoutDrawer, LabelsCheckboxFragment(), ADD, true)
  }
  
  override fun goToInfoFragment(word: Word?) {
    val infoFragment = if (word != null) InfoFragment.of(word) else InfoFragment()
    switchToFragment(R.id.layoutDrawer, infoFragment, REPLACE, true)
  }
  
  override fun onBackPressed() {
    if (layoutDrawer.isOpened()) {
      layoutDrawer.close()
    } else {
      if (supportFragmentManager.backStackEntryCount == 1) {
        textSearchWord.visible()
        textLabelName.gone()
        findFragment(InfoFragment::class)?.onBackPressed()
      }
      super.onBackPressed()
    }
  }
  
  private fun handleState(state: MainScreenState) {
    when (state) {
      is LoadedLabels -> {
        layoutLabelsDrawerStub.gone()
        recyclerDrawerLabels.visible()
        drawerButtonEditLabels.visible()
        labelsAdapter.submitList(state.labels)
      }
      is NoLabels -> {
        layoutLabelsDrawerStub.visible()
        recyclerDrawerLabels.gone()
        drawerButtonEditLabels.gone()
      }
    }
  }
  
  private fun onAdapterClick(label: Label) {
    textLabelName.text = label.name
    textSearchWord.gone()
    textLabelName.visible()
    switchToFragment(R.id.baseContainer, WordsForLabelFragment.of(label), REPLACE, true)
    layoutDrawer.close()
  }
  
  private fun performSetup() {
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    recyclerDrawerLabels.setupWith(labelsAdapter)
    layoutDrawer.setupToggle(this, toolbar)
    supportFragmentManager.addOnBackStackChangedListener {
      if (supportFragmentManager.backStackEntryCount == 0) {
        layoutDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
      } else {
        layoutDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
      }
    }
    drawerButtonAddLabels.setOnClickListener {
      transferToFragmentFomDrawer(LabelsFragment())
      layoutDrawer.close()
    }
    drawerButtonEditLabels.setOnClickListener {
      transferToFragmentFomDrawer(LabelsFragment())
      layoutDrawer.close()
    }
    textSearchWord.setOnClickListener {
      transferToFragmentFomDrawer(SearchFragment())
    }
  }
  
  private fun AppCompatActivity.transferToFragmentFomDrawer(fragment: Fragment) {
    switchToFragment(R.id.layoutDrawer, fragment, REPLACE, true)
  }
}