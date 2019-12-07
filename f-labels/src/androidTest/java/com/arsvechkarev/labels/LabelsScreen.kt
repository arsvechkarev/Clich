package com.arsvechkarev.labels

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher

class LabelsScreen : Screen<LabelsScreen>() {
  
  val fabNewLabel = KButton { withId(R.id.fabNewLabel) }
  
  
  val recyclerLabels = KRecyclerView(
    builder = { withId(R.id.recyclerLabels) },
    itemTypeBuilder = { itemType(::MainItem) }
  )
  
  class MainItem(parent: Matcher<View>) : KRecyclerItem<MainItem>(parent) {
    val textLabel = KTextView(parent) { withId(R.id.textLabel) }
  }
}