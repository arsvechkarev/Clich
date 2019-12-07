package com.arsvechkarev.clich.screens

import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.arsvechkarev.clich.R

class TheNewLabelDialogScreen : Screen<TheNewLabelDialogScreen>() {
  
  val editTextLabelName = KEditText { withId(R.id.editTextLabelName) }
  
  val buttonCreate = KButton { withId(R.id.buttonCreate) }
}