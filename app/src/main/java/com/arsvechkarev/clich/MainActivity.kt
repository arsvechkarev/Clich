package com.arsvechkarev.clich

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.fragment.app.Fragment
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.CoreActivity
import com.arsvechkarev.core.extensions.findFragment
import com.arsvechkarev.core.extensions.goToFragment
import com.arsvechkarev.core.extensions.isFragmentVisible
import com.arsvechkarev.core.extensions.observe
import com.arsvechkarev.core.extensions.setupToggle
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.switchFragment
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.list.Mode
import com.arsvechkarev.labels.presentation.LabelsFragment
import com.arsvechkarev.storage.database.CentralDatabase
import com.arsvechkarev.words.presentation.WordsListFragment
import kotlinx.android.synthetic.main.activity_main.baseContainer
import kotlinx.android.synthetic.main.activity_main.editTextSearchWord
import kotlinx.android.synthetic.main.activity_main.layoutDrawer
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.partial_layout_drawer.buttonGoToLabels
import kotlinx.android.synthetic.main.partial_layout_drawer.recyclerLabels
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity(), CoreActivity {
  
  private var labelsSelected = false
  
  override val snackBarPlace: View by lazy { baseContainer }
  
  private var wordsListFragment = WordsListFragment()
  private val labelsAdapter = LabelsAdapter(Mode.Simple {
    wordsListFragment.showWordsOf(it)
    labelsSelected = true
    layoutDrawer.close()
  })
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    layoutDrawer.setupToggle(this, toolbar)
    switchFragment(R.id.baseContainer, wordsListFragment)
    supportFragmentManager.addOnBackStackChangedListener {
      if (supportFragmentManager.backStackEntryCount == 0 && isFragmentVisible(WordsListFragment::class)) {
        editTextSearchWord.clearFocus()
        layoutDrawer.setDrawerLockMode(LOCK_MODE_UNLOCKED)
      }
    }
    buttonGoToLabels.setOnClickListener {
      layoutDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
      transferToFragment(LabelsFragment())
      layoutDrawer.close()
    }
    recyclerLabels.setupWith(labelsAdapter)
    CentralDatabase.instance.labelsDao().getAll().observe(this) {
      labelsAdapter.submitList(it)
    }
  }
  
  override fun <T : Fragment> goToFragmentFromRoot(
    fragment: Fragment,
    fragmentClass: KClass<T>,
    addToBackStack: Boolean
  ) {
    goToFragment(R.id.layoutDrawer, fragment, fragmentClass, addToBackStack)
    layoutDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
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
      if (labelsSelected && isFragmentVisible(WordsListFragment::class)) {
        wordsListFragment.showMainList()
        labelsSelected = false
      } else {
        findFragment(InfoFragment::class)?.onBackPressed()
        findFragment(WordsListFragment::class)?.onBackPressed()
        super.onBackPressed()
      }
    }
  }
  
  private fun applyLabelsFilter() {
    // TODO (22.11.2019): Do this
  }
}
