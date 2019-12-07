package com.arsvechkarev.clich.screens

import android.view.View
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.arsvechkarev.clich.R
import org.hamcrest.Matcher

class TheInfoScreen : Screen<TheInfoScreen>() {
  
  val imageBack = KImageView { withId(R.id.imageBack) }
  val textNewWord = KTextView { withId(R.id.textNewWord) }
  
  val editTextWord = KEditText { withId(R.id.editTextWord) }
  val editTextDefinition = KEditText { withId(R.id.editTextDefinition) }
  
  val buttonAddLabeds = KButton { withId(R.id.buttonAddLabels) }
  
  val recyclerLabels = KRecyclerView(
    builder = { withId(R.id.recyclerLabels) },
    itemTypeBuilder = { itemType(TheInfoScreen::MainItem) }
  )
  
  class MainItem(parent: Matcher<View>) : KRecyclerItem<MainItem>(parent) {
    val textLabel = KTextView(parent) { withId(R.id.textLabel) }
  }
}
