package com.arsvechkarev.clich.tests

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.arsvechkarev.clich.presentation.MainActivity
import com.arsvechkarev.clich.screens.LabelsCheckBoxScreen
import com.arsvechkarev.clich.screens.LabelsCheckBoxScreen.LabelsCheckBoxScreenItem
import com.arsvechkarev.clich.screens.WordInfoScreen
import com.arsvechkarev.clich.screens.WordInfoScreen.WordInfoScreenItem
import com.arsvechkarev.clich.screens.WordsListScreen
import com.arsvechkarev.clich.screens.WordsListScreen.WordsListScreenItemWord
import com.arsvechkarev.core.domain.dao.create
import com.arsvechkarev.testui.BaseTest
import com.arsvechkarev.testui.DatabaseRule
import com.arsvechkarev.testui.doAndWait
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SelectingLabelsTest : BaseTest {
  
  private val rule = object : ActivityTestRule<MainActivity>(MainActivity::class.java) {
    
    override fun beforeActivityLaunched() {
      doAndWait(1000) {
        labelsDao.create("Animals")
        labelsDao.create("Pets")
        labelsDao.create("House")
        labelsDao.create("Other")
        wordsDao.create("cat", "an animal")
      }
    }
  }
  
  @get:Rule
  val chain: RuleChain = RuleChain.outerRule(rule).around(DatabaseRule())
  
  @Test
  fun test1_Adding_labels_to_word() {
    onScreen<WordsListScreen> {
      recyclerWords {
        hasSize(1)
        firstChild<WordsListScreenItemWord> {
          hasSibling { withText("cat") }
          click()
        }
      }
    }
    
    onScreen<WordInfoScreen> {
      recyclerWordsLabels.hasSize(0)
      buttonAddLabels.click()
    }
    
    onScreen<LabelsCheckBoxScreen> {
      recyclerLabels {
        hasSize(4)
        sleep(500)
        // [Start Workaround]
        // Clicking on label, because otherwise test doesn't work
        // (Manual clicking works fine)
        childWith<LabelsCheckBoxScreenItem> { withDescendant { withText("House") } } perform {
          checkbox.click()
        }
        // [End Workaround]
        childWith<LabelsCheckBoxScreenItem> { withDescendant { withText("Animals") } } perform {
          checkbox.click()
        }
        childWith<LabelsCheckBoxScreenItem> { withDescendant { withText("Pets") } } perform {
          checkbox.click()
        }
      }
      pressBack()
    }
    
    sleep(1000)
    
    onScreen<WordInfoScreen> {
      recyclerWordsLabels {
        hasSize(2)
        childWith<WordInfoScreenItem> { withDescendant { withText("Animals") } } perform { isVisible() }
        childWith<WordInfoScreenItem> { withDescendant { withText("Pets") } } perform { isVisible() }
      }
      
      buttonAddLabels.click()
    }
    
    onScreen<LabelsCheckBoxScreen> {
      recyclerLabels {
        childWith<LabelsCheckBoxScreenItem> { withDescendant { withText("Animals") } } perform {
          checkbox {
            isChecked()
            click()
          }
        }
        childWith<LabelsCheckBoxScreenItem> { withDescendant { withText("Pets") } } perform {
          checkbox {
            isChecked()
            click()
          }
        }
        childWith<LabelsCheckBoxScreenItem> { withDescendant { withText("House") } } perform {
          checkbox.isNotChecked()
        }
        childWith<LabelsCheckBoxScreenItem> { withDescendant { withText("Other") } } perform {
          checkbox.isNotChecked()
        }
      }
      
      pressBack()
    }
    
    onScreen<WordInfoScreen> {
      recyclerWordsLabels { hasSize(0) }
    }
  }
}