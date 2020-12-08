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
  
  val fabNewWord = KButton { withId(R.id.wordsFabNewWord) }
  val layoutStub = KView { withId(R.id.wordsLayoutLoading) }
  
  val recyclerWords = KRecyclerView(
    builder = { withId(R.id.wordsRecycler) },
    itemTypeBuilder = {
      itemType(WordsListScreen::WordsListScreenItemWord)
      itemType(WordsListScreen::WordsListScreenItemTimeDivider)
    }
  )
  
  class WordsListScreenItemWord(parent: Matcher<View>) :
    KRecyclerItem<WordsListScreenItemWord>(parent) {
    val textWord = KTextView(parent) { withId(R.id.textWord) }
  }
  
  class WordsListScreenItemTimeDivider(parent: Matcher<View>) :
    KRecyclerItem<WordsListScreenItemTimeDivider>(parent) {
    val textDate = KTextView(parent) { withId(R.id.textDate) }
  }
  
}
