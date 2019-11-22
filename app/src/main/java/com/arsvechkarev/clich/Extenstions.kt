package com.arsvechkarev.clich

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.arsvechkarev.core.extensions.switchFragment

fun MainActivity.transferToFragment(fragment: Fragment) {
  switchFragment(R.id.layoutDrawer, fragment, true)
}

fun DrawerLayout.close() {
  closeDrawer(GravityCompat.START)
}

fun DrawerLayout.isOpen(): Boolean {
  return isDrawerOpen(GravityCompat.START)
}

