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

class WordsListScreen : Screen<WordsListScreen>() {
  
  val fabNewWord = KButton { withId(R.id.fabNewWord) }
  val layoutStub = KView { withId(R.id.layoutStub) }
  
  val recyclerWords = KRecyclerView(
    builder = { withId(R.id.recyclerWords) },
    itemTypeBuilder = { itemType(WordsListScreen::WordsListScreenItem) }
  )
  
  class WordsListScreenItem(parent: Matcher<View>) : KRecyclerItem<WordsListScreenItem>(parent) {
    val textWord = KTextView(parent) { withId(R.id.textWord) }
  }
  
}
