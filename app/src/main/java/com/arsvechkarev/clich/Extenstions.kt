package com.arsvechkarev.clich

import androidx.fragment.app.Fragment
import com.arsvechkarev.core.extensions.switchFragment

fun MainActivity.transferToFragment(fragment: Fragment) {
  switchFragment(R.id.layoutDrawer, fragment, true)
}