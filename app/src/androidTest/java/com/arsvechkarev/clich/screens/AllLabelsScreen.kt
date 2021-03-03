package com.arsvechkarev.clich.screens

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.arsvechkarev.clich.R
import org.hamcrest.Matcher

class AllLabelsScreen : Screen<AllLabelsScreen>() {
  
  val fabNewLabel = KButton { withId(R.id.fabNewLabel) }
  val layoutLabelsStub = KView { withId(R.id.layoutLabelsStub) }
  
  val arrowBack = KView { withContentDescription(R.string.abc_action_bar_up_description) }
  
  val recyclerLabels = KRecyclerView(
    builder = { withId(R.id.recyclerLabels) },
    itemTypeBuilder = { itemType(::AllLabelsScreenItem) }
  )
  
  class AllLabelsScreenItem(parent: Matcher<View>) : KRecyclerItem<AllLabelsScreenItem>(parent) {
    val textLabel = KTextView(parent) { withId(R.id.textLabel) }
  
    val editTextLabel = KEditText(parent) { withId(R.id.editTextLabel) }
  
    val imageStart = KImageView(parent) { withId(R.id.imageStart) }
    val imageEnd = KImageView(parent) { withId(R.id.imageEnd) }
  
    val dividerTop = KEditText(parent) { withId(R.id.dividerTop) }
    val dividerBottom = KEditText(parent) { withId(R.id.dividerBottom) }
  }
}