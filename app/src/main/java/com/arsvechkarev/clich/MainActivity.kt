package com.arsvechkarev.clich

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.CoreActivity
import com.arsvechkarev.core.extensions.findFragment
import com.arsvechkarev.core.extensions.goToFragment
import com.arsvechkarev.core.extensions.setupToggle
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.list.presentation.WordsListFragment
import kotlinx.android.synthetic.main.activity_main.baseContainer
import kotlinx.android.synthetic.main.activity_main.layoutDrawer
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity(), CoreActivity {
  
  override val snackBarPlace: View by lazy { baseContainer }
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    layoutDrawer.setupToggle(this, toolbar)
  }
  
  
  override fun <T : Fragment> goToFragmentFromRoot(
    fragment: Fragment,
    fragmentClass: KClass<T>,
    addToBackStack: Boolean
  ) {
    goToFragment(R.id.baseContainer, fragment, fragmentClass, addToBackStack)
  }
  
  override fun <T : BaseFragment> subscribeOnBackStackChanges(fragment: T) {
    supportFragmentManager.addOnBackStackChangedListener {
      fragment.update()
    }
  }
  
  override fun onBackPressed() {
    findFragment(InfoFragment::class)?.onBackPressed()
    findFragment(WordsListFragment::class)?.onBackPressed()
    super.onBackPressed()
  }
}