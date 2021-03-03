package com.arsvechkarev.clich.screens

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.arsvechkarev.clich.R
import org.hamcrest.Matcher

class WordsListScreen : Screen<WordsListScreen>() {
  
  val fabNewWord = KButton { withId(R.id.wordsFabNewWord) }
  val layoutNoWords = KView { withId(R.id.wordsListLayoutNoWords) }
  
  val recyclerWords = KRecyclerView(
    builder = { withId(R.id.wordsListRecycler) },
    itemTypeBuilder = { itemType(WordsListScreen::WordsListScreenItemWord) }
  )
  
  class WordsListScreenItemWord(parent: Matcher<View>) :
    KRecyclerItem<WordsListScreenItemWord>(parent)
}