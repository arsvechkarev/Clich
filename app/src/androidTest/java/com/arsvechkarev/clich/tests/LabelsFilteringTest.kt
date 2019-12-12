package com.arsvechkarev.clich.tests

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.arsvechkarev.clich.MainActivity
import com.arsvechkarev.clich.screens.DrawerScreen
import com.arsvechkarev.clich.screens.DrawerScreen.DrawerScreenItem
import com.arsvechkarev.clich.screens.MainScreen
import com.arsvechkarev.clich.screens.WordInfoScreen
import com.arsvechkarev.clich.screens.WordInfoScreen.WordInfoScreenItem
import com.arsvechkarev.clich.screens.WordsListScreen
import com.arsvechkarev.clich.screens.WordsListScreen.WordsListScreenItem
import com.arsvechkarev.core.domain.dao.create
import com.arsvechkarev.storage.database.CentralDatabase
import com.arsvechkarev.testui.DatabaseRule
import com.arsvechkarev.testui.doAndWait
import com.arsvechkarev.testui.screen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LabelsFilteringTest {
  
  private val database = CentralDatabase.instance
  
  @get:Rule
  val chain: RuleChain = RuleChain.outerRule(ActivityTestRule(MainActivity::class.java))
    .around(DatabaseRule())
  
  @Test
  fun text1_Filter_test() {
    doAndWait(1000) {
      val labelIronManId = database.labelsDao().create("Iron Man")
      val labelAnimalsId = database.labelsDao().create("Animals")
      
      val wordIronId = database.wordDao().create("iron")
      val wordSuitId = database.wordDao().create("suit")
      
      val wordBearId = database.wordDao().create("bear")
      val wordFoxId = database.wordDao().create("fox")
      val wordBirdId = database.wordDao().create("bird")
      
      database.wordDao().create("other stuff")
      
      database.wordsAndLabelsDao().create(wordIronId, labelIronManId)
      database.wordsAndLabelsDao().create(wordSuitId, labelIronManId)
      
      database.wordsAndLabelsDao().create(wordBearId, labelAnimalsId)
      database.wordsAndLabelsDao().create(wordFoxId, labelAnimalsId)
      database.wordsAndLabelsDao().create(wordBirdId, labelAnimalsId)
    }
    
    onScreen<WordsListScreen> {
      recyclerWords.hasSize(6)
      screen<MainScreen>().drawer.open()
      
      // Clicking iron man label
      onScreen<DrawerScreen> {
        recyclerDrawerLabels {
          hasSize(2)
          childWith<DrawerScreenItem> { withDescendant { withText("Iron Man") } } perform { click() }
        }
      }
      
      recyclerWords {
        hasSize(2)
        childWith<WordsListScreenItem> { withDescendant { withText("suit") } } perform {
          isDisplayed()
        }
        childWith<WordsListScreenItem> { withDescendant { withText("iron") } } perform {
          isDisplayed()
        }
      }
      
      pressBack()
      
      recyclerWords {
        hasSize(6)
      }
      
      screen<MainScreen>().drawer.open()
      
      onScreen<DrawerScreen> {
        recyclerDrawerLabels {
          hasSize(2)
          childWith<DrawerScreenItem> { withDescendant { withText("Animals") } } perform { click() }
        }
      }
      
      recyclerWords {
        hasSize(3)
        childWith<WordsListScreenItem> { withDescendant { withText("fox") } } perform { isDisplayed() }
        childWith<WordsListScreenItem> { withDescendant { withText("bird") } } perform { isDisplayed() }
        childWith<WordsListScreenItem> { withDescendant { withText("bear") } } perform {
          isDisplayed()
          click()
        }
      }
      
      onScreen<WordInfoScreen> {
        editTextWord.hasText("bear")
        recyclerWordsLabels {
          hasSize(1)
          firstChild<WordInfoScreenItem> {
            textLabel.hasText("Animals")
          }
        }
        pressBack()
      }
      
      recyclerWords {
        hasSize(3)
        childWith<WordsListScreenItem> { withDescendant { withText("fox") } } perform { isDisplayed() }
        childWith<WordsListScreenItem> { withDescendant { withText("bird") } } perform { isDisplayed() }
        childWith<WordsListScreenItem> { withDescendant { withText("bear") } } perform { isDisplayed() }
      }
      
      pressBack()
      
      recyclerWords.hasSize(6)
    }
  }
}