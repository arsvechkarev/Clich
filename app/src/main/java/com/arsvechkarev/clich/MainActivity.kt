package com.arsvechkarev.clich

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.fragment.app.Fragment
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.CoreActivity
import com.arsvechkarev.core.extensions.close
import com.arsvechkarev.core.extensions.findFragment
import com.arsvechkarev.core.extensions.goToFragment
import com.arsvechkarev.core.extensions.gone
import com.arsvechkarev.core.extensions.isOpen
import com.arsvechkarev.core.extensions.observe
import com.arsvechkarev.core.extensions.setupToggle
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.switchFragment
import com.arsvechkarev.core.extensions.visible
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.list.Mode
import com.arsvechkarev.labels.presentation.LabelsFragment
import com.arsvechkarev.search.presentation.SearchFragment
import com.arsvechkarev.storage.CentralDatabase
import com.arsvechkarev.words.presentation.WordsListFragment
import kotlinx.android.synthetic.main.activity_main.baseContainer
import kotlinx.android.synthetic.main.activity_main.layoutDrawer
import kotlinx.android.synthetic.main.activity_main.textLabelName
import kotlinx.android.synthetic.main.activity_main.textSearchWord
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.partial_layout_drawer.layoutGoToLabels
import kotlinx.android.synthetic.main.partial_layout_drawer.layoutLabelsDrawerStub
import kotlinx.android.synthetic.main.partial_layout_drawer.recyclerDrawerLabels
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity(), CoreActivity {
  
  override val snackBarPlace: View by lazy { baseContainer }
  
  private var wordsListFragment = WordsListFragment()
  private val labelsAdapter = LabelsAdapter(Mode.Simple { label ->
    textLabelName.text = label.name
    textSearchWord.gone()
    textLabelName.visible()
    switchFragment(R.id.baseContainer, WordsListFragment.of(label), true)
    layoutDrawer.close()
  })
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    layoutDrawer.setupToggle(this, toolbar)
    switchFragment(R.id.baseContainer, wordsListFragment)
    supportFragmentManager.addOnBackStackChangedListener {
      if (supportFragmentManager.backStackEntryCount == 0) {
        layoutDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
      } else {
        layoutDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
      }
    }
    layoutGoToLabels.setOnClickListener {
      transferToFragment(LabelsFragment())
      layoutDrawer.close()
    }
    recyclerDrawerLabels.setupWith(labelsAdapter)
    CentralDatabase.instance.labelsDao().getAll().observe(this) {
      if (it.isEmpty()) {
        layoutLabelsDrawerStub.visible()
        recyclerDrawerLabels.gone()
      } else {
        layoutLabelsDrawerStub.gone()
        recyclerDrawerLabels.visible()
        labelsAdapter.submitList(it)
      }
    }
    textSearchWord.setOnClickListener {
      transferToFragment(SearchFragment())
    }
  }
  
  override fun <T : Fragment> goToFragmentFromRoot(
    fragment: Fragment,
    fragmentClass: KClass<T>,
    addToBackStack: Boolean
  ) {
    goToFragment(R.id.layoutDrawer, fragment, fragmentClass, addToBackStack)
  }
  
  override fun <T : BaseFragment> subscribeOnBackStackChanges(fragment: T) {
    supportFragmentManager.addOnBackStackChangedListener {
      fragment.onBackStackUpdate()
    }
  }
  
  override fun onBackPressed() {
    if (layoutDrawer.isOpen()) {
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
  
}
