package com.arsvechkarev.clich.screens

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.arsvechkarev.clich.R
import org.hamcrest.Matcher

class WordsListScreen : Screen<WordsListScreen>() {
  
  val fabNewWord = KButton { withId(R.id.fabNewWord) }
  
  val recyclerWords = KRecyclerView(
    builder = { withId(R.id.recyclerWords) },
    itemTypeBuilder = { itemType(WordsListScreen::WordItem) }
  )
  
  class WordItem(parent: Matcher<View>) : KRecyclerItem<WordItem>(parent) {
    val textWord = KTextView(parent) { withId(R.id.textWord) }
  }
  
}
