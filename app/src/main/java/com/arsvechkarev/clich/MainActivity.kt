package com.arsvechkarev.clich

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.CoreActivity
import com.arsvechkarev.core.extensions.findFragment
import com.arsvechkarev.core.extensions.goToFragment
import com.arsvechkarev.core.extensions.observe
import com.arsvechkarev.core.extensions.setupToggle
import com.arsvechkarev.core.extensions.setupWith
import com.arsvechkarev.core.extensions.switchFragment
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.labels.list.LabelCallback
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.presentation.LabelsFragment
import com.arsvechkarev.storage.database.CentralDatabase
import com.arsvechkarev.words.presentation.WordsListFragment
import kotlinx.android.synthetic.main.activity_main.baseContainer
import kotlinx.android.synthetic.main.activity_main.buttonGoToLabels
import kotlinx.android.synthetic.main.activity_main.layoutDrawer
import kotlinx.android.synthetic.main.activity_main.recyclerLabels
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity(), CoreActivity {
  
  private val labelsAdapter = LabelsAdapter(object : LabelCallback{})
  
  override val snackBarPlace: View by lazy { baseContainer }
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    layoutDrawer.setupToggle(this, toolbar)
    switchFragment(R.id.baseContainer, WordsListFragment())
    buttonGoToLabels.setOnClickListener {
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
  }
  
  override fun <T : BaseFragment> subscribeOnBackStackChanges(fragment: T) {
    supportFragmentManager.addOnBackStackChangedListener {
      fragment.update()
    }
  }
  
  override fun onBackPressed() {
    if (layoutDrawer.isOpen()) {
      layoutDrawer.close()
    } else {
      findFragment(InfoFragment::class)?.onBackPressed()
      findFragment(WordsListFragment::class)?.onBackPressed()
      super.onBackPressed()
    }
  }
  
  private fun applyLabelsFilter() {
    // TODO (22.11.2019): Do this
  }
}
