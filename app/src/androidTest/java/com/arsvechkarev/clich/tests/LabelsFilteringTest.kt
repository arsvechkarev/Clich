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
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.WordsLabelsJoin
import com.arsvechkarev.storage.database.CentralDatabase
import com.arsvechkarev.testui.screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.lang.Thread.sleep

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LabelsFilteringTest {
  
  @get:Rule
  val activityRule = ActivityTestRule(MainActivity::class.java)
  
  @Test
  fun text1_Filter_test() {
    
    GlobalScope.launch(Dispatchers.IO) {
      
      CentralDatabase.instance.clearAllTables()
      
      val labelIronManId = CentralDatabase.instance.labelsDao().create(Label(name = "Iron Man"))
      val labelAnimalsId = CentralDatabase.instance.labelsDao().create(Label(name = "Animals"))
      
      val wordIronId = CentralDatabase.instance.wordDao().create(Word(name = "iron"))
      val wordSuitId = CentralDatabase.instance.wordDao().create(Word(name = "suit"))
      
      val wordBearId = CentralDatabase.instance.wordDao().create(Word(name = "bear"))
      val wordFoxId = CentralDatabase.instance.wordDao().create(Word(name = "fox"))
      val wordBirdId = CentralDatabase.instance.wordDao().create(Word(name = "bird"))
      
      CentralDatabase.instance.wordDao().create(Word(name = "other stuff"))
      
      CentralDatabase.instance.wordsAndLabelsDao()
        .create(WordsLabelsJoin(wordIronId, labelIronManId))
      CentralDatabase.instance.wordsAndLabelsDao()
        .create(WordsLabelsJoin(wordSuitId, labelIronManId))
      
      CentralDatabase.instance.wordsAndLabelsDao()
        .create(WordsLabelsJoin(wordBearId, labelAnimalsId))
      CentralDatabase.instance.wordsAndLabelsDao()
        .create(WordsLabelsJoin(wordFoxId, labelAnimalsId))
      CentralDatabase.instance.wordsAndLabelsDao()
        .create(WordsLabelsJoin(wordBirdId, labelAnimalsId))
    }
    
    sleep(1000)
    
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