package com.arsvechkarev.clich.screens

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.arsvechkarev.clich.R
import org.hamcrest.Matcher

class AllLabelsScreen : Screen<AllLabelsScreen>() {
  
  val fabNewLabel = KButton { withId(R.id.fabNewLabel) }
  
  val arrowBack = KView { withContentDescription(R.string.abc_action_bar_up_description) }
  
  val recyclerLabels = KRecyclerView(
    builder = { withId(R.id.recyclerLabels) },
    itemTypeBuilder = { itemType(::MainItem) }
  )
  
  class MainItem(parent: Matcher<View>) : KRecyclerItem<MainItem>(parent) {
    val textLabel = KTextView(parent) { withId(R.id.textLabel) }
  }
}