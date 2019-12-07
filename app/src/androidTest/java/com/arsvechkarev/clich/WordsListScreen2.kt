package com.arsvechkarev.clich

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher

class WordsListScreen2 : Screen<WordsListScreen2>() {
  
  val fabNewWord = KButton { withId(R.id.fabNewWord) }
  
  val recyclerLabels = KRecyclerView(
    builder = { withId(R.id.recyclerWords) },
    itemTypeBuilder = { itemType(::MainItem) }
  )
  
  class MainItem(parent: Matcher<View>) : KRecyclerItem<MainItem>(parent) {
    val textWord = KTextView(parent) { withId(R.id.textWord) }
  }
  
}
