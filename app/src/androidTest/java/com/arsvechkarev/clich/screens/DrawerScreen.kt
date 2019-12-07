package com.arsvechkarev.clich.screens

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.arsvechkarev.clich.R
import org.hamcrest.Matcher

class DrawerScreen : Screen<DrawerScreen>() {
  
  val buttonLabels = KButton { withId(R.id.buttonGoToLabels) }
  
  val recyclerDrawerLabels = KRecyclerView(
    builder = { withId(R.id.recyclerDrawerLabels) },
    itemTypeBuilder = { itemType(DrawerScreen::RecyclerDrawerItem) }
  )
  
  class RecyclerDrawerItem(parent: Matcher<View>) : KRecyclerItem<RecyclerDrawerItem>(parent) {
    val textLabel = KTextView(parent) { withId(R.id.textLabel) }
  }
}
