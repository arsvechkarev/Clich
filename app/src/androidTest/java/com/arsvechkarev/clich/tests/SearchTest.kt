package com.arsvechkarev.clich.tests

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.arsvechkarev.clich.presentation.MainActivity
import com.arsvechkarev.clich.screens.MainScreen
import com.arsvechkarev.clich.screens.SearchScreen
import com.arsvechkarev.clich.screens.SearchScreen.SearchScreenItem
import com.arsvechkarev.core.domain.dao.create
import com.arsvechkarev.testui.BaseTest
import com.arsvechkarev.testui.DatabaseRule
import com.arsvechkarev.testui.doAndWait
import com.arsvechkarev.testui.screen
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SearchTest : BaseTest {
  
  private val rule = object : ActivityTestRule<MainActivity>(MainActivity::class.java) {
    
    override fun beforeActivityLaunched() {
      doAndWait(500) {
        database.wordsDao().create("apple")
        database.wordsDao().create("appreciate")
        database.wordsDao().create("africa")
        database.wordsDao().create("suit")
        database.wordsDao().create("bear")
        database.wordsDao().create("fox")
        database.wordsDao().create("bird")
        database.wordsDao().create("other stuff")
      }
    }
  }
  
  @get:Rule
  val chain: RuleChain = RuleChain.outerRule(rule).around(DatabaseRule())
  
  @Test
  fun test1_Search() {
    screen<MainScreen>().textSearchWord.click()
    onScreen<SearchScreen> {
      searchEditText.typeText("app")
      layoutNoWordsFound.isNotDisplayed()
      recyclerFoundWords {
        hasSize(2)
        childAt<SearchScreenItem>(0) { hasSibling { withText("apple") } }
        childAt<SearchScreenItem>(1) { hasSibling { withText("appreciate") } }
      }
      searchEditText.clearText()
      layoutNoWordsFound.isInvisible()
      searchEditText.typeText("s")
      recyclerFoundWords {
        hasSize(2)
        childAt<SearchScreenItem>(0) { hasSibling { withText("suit") } }
        childAt<SearchScreenItem>(1) { hasSibling { withText("other stuff") } }
      }
    }
  }
}