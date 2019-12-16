package com.arsvechkarev.clich.screens

import com.agoda.kakao.drawer.KDrawerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.arsvechkarev.clich.R

class MainScreen : Screen<MainScreen>() {
  
  val drawer = KDrawerView { withId(R.id.layoutDrawer) }
  
  val textSearchWord = KTextView { withId(R.id.textSearchWord) }
  val textLabelName = KTextView { withId(R.id.textLabelName) }
}
