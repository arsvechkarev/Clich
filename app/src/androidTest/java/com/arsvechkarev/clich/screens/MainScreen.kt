package com.arsvechkarev.clich.screens

import com.agoda.kakao.drawer.KDrawerView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.screen.Screen
import com.arsvechkarev.clich.R

class MainScreen : Screen<MainScreen>() {
  
  val drawer = KDrawerView { withId(R.id.layoutDrawer) }
  
  val searchEditText = KEditText { withId(R.id.editTextSearchWord) }
  
}
