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

class WordInfoScreen : Screen<WordInfoScreen>() {
  
  val imageBack = KImageView { withId(R.id.imageBack) }
  val textNewWord = KTextView { withId(R.id.textNewWord) }
  val imageMenu = KImageView { withId(R.id.imageMenu) }
  
  val editTextWord = KEditText { withId(R.id.editTextWord) }
  val editTextDefinition = KEditText { withId(R.id.editTextDefinition) }
  val editTextExamples = KEditText { withId(R.id.editTextExamples) }
  
  val buttonAddLabels = KButton { withId(R.id.buttonAddLabels) }
  
  val recyclerWordsLabels = KRecyclerView(
    builder = { withId(R.id.recyclerWordsLabels) },
    itemTypeBuilder = { itemType(::WordInfoScreenItem) }
  )
  
  class WordInfoScreenItem(parent: Matcher<View>) : KRecyclerItem<WordInfoScreenItem>(parent) {
    val textLabel = KTextView(parent) { withId(R.id.textLabel) }
  }
}
