package com.arsvechkarev.clich.tests

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.arsvechkarev.clich.presentation.MainActivity
import com.arsvechkarev.clich.screens.DrawerScreen
import com.arsvechkarev.clich.screens.DrawerScreen.DrawerScreenItem
import com.arsvechkarev.clich.screens.MainScreen
import com.arsvechkarev.clich.screens.SearchScreen.SearchScreenItem
import com.arsvechkarev.clich.screens.WordInfoScreen
import com.arsvechkarev.clich.screens.WordInfoScreen.WordInfoScreenItem
import com.arsvechkarev.clich.screens.WordsListScreen
import com.arsvechkarev.clich.screens.WordsListScreen.WordsListScreenItemWord
import com.arsvechkarev.core.domain.dao.create
import com.arsvechkarev.testui.BaseTest
import com.arsvechkarev.testui.DatabaseRule
import com.arsvechkarev.testui.doAndWait
import com.arsvechkarev.testui.isVisibleAndHasText
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
class LabelsFilteringTest : BaseTest {
  
  private val rule = object : ActivityTestRule<MainActivity>(MainActivity::class.java) {
    
    override fun beforeActivityLaunched() {
      
      doAndWait(2000) {
        val labelIronManId = labelsDao.create("Iron Man")
        val labelAnimalsId = labelsDao.create("Animals")
        
        val wordIronId = wordsDao.create("iron")
        val wordSuitId = wordsDao.create("suit")
        
        val wordBearId = wordsDao.create("bear")
        val wordFoxId = wordsDao.create("fox")
        val wordBirdId = wordsDao.create("bird")
        
        wordsDao.create("other stuff")
        
        wordsLabelsDao.create(wordIronId, labelIronManId)
        wordsLabelsDao.create(wordSuitId, labelIronManId)
        
        wordsLabelsDao.create(wordBearId, labelAnimalsId)
        wordsLabelsDao.create(wordFoxId, labelAnimalsId)
        wordsLabelsDao.create(wordBirdId, labelAnimalsId)
      }
    }
  }
  
  @get:Rule
  val chain: RuleChain = RuleChain.outerRule(rule).around(DatabaseRule())
  
  @Test
  fun test1_Filtering() {
    
    onScreen<WordsListScreen> {
      recyclerWords.hasSize(6)
      screen<MainScreen>().drawer.open()
      
      // Clicking iron man label
      onScreen<DrawerScreen> {
        recyclerDrawerLabels {
          hasSize(2)
          childAt<DrawerScreenItem>(0) {
            hasDescendant { withText("Iron Man") }
            click()
          }
        }
      }
      
      onScreen<MainScreen> {
        textLabelName isVisibleAndHasText "Iron Man"
        textSearchWord.isNotDisplayed()
      }
      
      recyclerWords {
        hasSize(2)
        childAt<WordsListScreenItemWord>(0) { hasSibling { withText("iron") } }
        childAt<WordsListScreenItemWord>(1) { hasSibling { withText("suit") } }
      }
      
      pressBack()
      
      onScreen<MainScreen> {
        textLabelName.isNotDisplayed()
        textSearchWord.isVisible()
      }
      
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
        childWith<WordsListScreenItemWord> { containsText("bird") }
        childWith<WordsListScreenItemWord> { containsText("fox") }
        childWith<WordsListScreenItemWord> {
          containsText("bear")
        } perform {
          click()
        }
//        childWith<WordsListScreenItemWord> { withSibling { withText("bird") } } perform { isVisible() }
//        childWith<WordsListScreenItemWord> { withSibling { withText("fox") } } perform { isVisible() }
//        childWith<WordsListScreenItemWord> { withSibling { withText("bear") } } perform {
//          isVisible()
//          click()
//        }
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
        childAt<WordsListScreenItemWord>(0) { hasSibling { withText("bird") } }
        childAt<WordsListScreenItemWord>(1) { hasSibling { withText("fox") } }
        childAt<WordsListScreenItemWord>(2) { hasSibling { withText("bear") } }
      }
      
      pressBack()
      
      recyclerWords.hasSize(6)
    }
  }
}