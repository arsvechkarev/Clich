package com.arsvechkarev.testui

import androidx.annotation.StringRes
import com.agoda.kakao.common.views.KBaseView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.text.KTextView

fun <T> assertNotDisplayed(vararg views: KBaseView<T>) {
  views.forEach { it.isNotDisplayed() }
}

fun <T> assertDisplayed(vararg views: KBaseView<T>) {
  views.forEach { it.isDisplayed() }
}

infix fun <T : KBaseView<T>> T.isDisplayedAnd(block: T.() -> Unit) {
  apply {
    isDisplayed()
    block()
  }
}

infix fun KTextView.isDisplayedAndHasText(text: String) {
  apply {
    isDisplayed()
    hasText(text)
  }
}

infix fun KTextView.isDisplayedAndHasText(@StringRes textResId: Int) {
  apply {
    isDisplayed()
    hasText(textResId)
  }
}

infix fun KEditText.isDisplayedAndHasHint(text: String) {
  apply {
    isDisplayed()
    hasHint(text)
  }
}

infix fun KEditText.isDisplayedAndHasHint(@StringRes textResId: Int) {
  apply {
    isDisplayed()
    hasHint(textResId)
  }
}

infix fun KEditText.clearAndTypeText(text: String) {
  apply {
    clearText()
    typeText(text)
  }
}