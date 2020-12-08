package com.arsvechkarev.clich.screens

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.arsvechkarev.clich.R
import org.hamcrest.Matcher

class DrawerScreen : Screen<DrawerScreen>() {
  
  val buttonCreateLabel = KView { withId(R.id.drawerButtonCreateLabel) }
  
  val recyclerDrawerLabels = KRecyclerView(
    builder = { withId(R.id.recyclerDrawerLabels) },
    itemTypeBuilder = { itemType(::DrawerScreenItem) }
  )
  
  class DrawerScreenItem(parent: Matcher<View>) : KRecyclerItem<DrawerScreenItem>(parent) {
    val textLabel = KTextView(parent) { withId(R.id.textLabel) }
  }
}
