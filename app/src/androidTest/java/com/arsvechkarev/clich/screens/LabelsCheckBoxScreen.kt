package com.arsvechkarev.clich.screens

import android.view.View
import com.agoda.kakao.check.KCheckBox
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.arsvechkarev.clich.R
import org.hamcrest.Matcher

class LabelsCheckBoxScreen : Screen<LabelsCheckBoxScreen>() {
  
  val recyclerLabels = KRecyclerView(
    builder = { withId(R.id.recyclerLabels) },
    itemTypeBuilder = { itemType(::LabelsCheckBoxScreenItem) }
  )
  
  class LabelsCheckBoxScreenItem(parent: Matcher<View>) :
    KRecyclerItem<LabelsCheckBoxScreenItem>(parent) {
    
    val textLabel = KTextView(parent) { withId(R.id.textLabel) }
    val checkbox = KCheckBox(parent) { withId(R.id.checkbox) }
  }
}