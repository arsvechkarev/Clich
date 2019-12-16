package com.arsvechkarev.clich.tests

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.arsvechkarev.clich.MainActivity
import com.arsvechkarev.clich.screens.MainScreen
import com.arsvechkarev.clich.screens.SearchScreen
import com.arsvechkarev.core.domain.dao.create
import com.arsvechkarev.storage.database.CentralDatabase
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
class SearchTest {
  
  private val database = CentralDatabase.instance
  
  @get:Rule
  val chain: RuleChain = RuleChain.outerRule(ActivityTestRule(MainActivity::class.java))
    .around(DatabaseRule())
  
  @Test
  fun test1_Search() {
    doAndWait(500) {
      database.wordDao().create("apple")
      database.wordDao().create("appreciate")
      database.wordDao().create("africa")
      database.wordDao().create("suit")
      database.wordDao().create("bear")
      database.wordDao().create("fox")
      database.wordDao().create("bird")
      database.wordDao().create("other stuff")
    }
    
    screen<MainScreen>().textSearchWord.click()
    
    onScreen<SearchScreen> {
      searchEditText.typeText("app")
      
      layoutNoWordsFound.isNotDisplayed()
      recyclerFoundWords {
        hasSize(2)
        childWith<SearchScreen.SearchScreenItem> { withDescendant { withText("apple") } } perform { isVisible() }
        childWith<SearchScreen.SearchScreenItem> { withDescendant { withText("appreciate") } } perform { isVisible() }
      }
      
      searchEditText.clearText()
  
      layoutNoWordsFound.isInvisible()
      
      searchEditText.typeText("s")
      
      recyclerFoundWords {
        hasSize(2)
        childWith<SearchScreen.SearchScreenItem> { withDescendant { withText("suit") } } perform { isVisible() }
        childWith<SearchScreen.SearchScreenItem> { withDescendant { withText("other stuff") } } perform { isVisible() }
      }
    }
    
  }
  
}