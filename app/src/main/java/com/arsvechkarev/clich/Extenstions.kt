package com.arsvechkarev.clich

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arsvechkarev.core.extensions.switchToFragment

fun AppCompatActivity.transferToFragment(fragment: Fragment) {
  switchToFragment(R.id.layoutDrawer, fragment, true)
}
