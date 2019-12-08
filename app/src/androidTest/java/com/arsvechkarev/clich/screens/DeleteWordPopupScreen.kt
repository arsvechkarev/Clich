package com.arsvechkarev.clich.screens

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.screen.Screen
import com.arsvechkarev.clich.R

class DeleteWordPopupScreen : Screen<DeleteWordPopupScreen>() {
  
  val deleteWordItem = KView {
    withText(R.string.text_delete_word)
  }
  
}