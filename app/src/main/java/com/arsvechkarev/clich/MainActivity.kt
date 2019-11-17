package com.arsvechkarev.clich

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arsvechkarev.core.CoreActivity
import com.arsvechkarev.core.extensions.switchFragment
import com.arsvechkarev.list.presentation.WordsListFragment
import kotlinx.android.synthetic.main.activity_main.baseContainer

class MainActivity : AppCompatActivity(), CoreActivity {
  
  override val snackBarPlace: View by lazy { baseContainer }
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    switchFragment(R.id.baseContainer, WordsListFragment())
  }
  
  override fun goToFragmentFromRoot(fragment: Fragment, addToBackStack: Boolean) {
    switchFragment(R.id.baseContainer, fragment, addToBackStack)
  }
}
