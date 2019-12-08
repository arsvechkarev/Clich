package com.arsvechkarev.clich.screens

import android.view.View
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.arsvechkarev.clich.R
import org.hamcrest.Matcher

class SearchScreen : Screen<SearchScreen>() {
  
  val searchEditText = KEditText { withId(R.id.searchEditText) }
  
  val recyclerFoundWords = KRecyclerView(
    builder = { withId(R.id.recyclerFoundWords) },
    itemTypeBuilder = { itemType(SearchScreen::SearchScreenItem) }
  )
  
  class SearchScreenItem(parent: Matcher<View>) : KRecyclerItem<SearchScreenItem>(parent) {
    val textWord = KTextView(parent) { withId(R.id.textWord) }
  }
  
}