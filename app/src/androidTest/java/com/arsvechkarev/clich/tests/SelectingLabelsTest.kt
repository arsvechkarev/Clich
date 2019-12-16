package com.arsvechkarev.clich.tests

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.arsvechkarev.clich.MainActivity
import com.arsvechkarev.clich.screens.LabelsCheckBoxScreen
import com.arsvechkarev.clich.screens.LabelsCheckBoxScreen.LabelsCheckBoxScreenItem
import com.arsvechkarev.clich.screens.WordInfoScreen
import com.arsvechkarev.clich.screens.WordInfoScreen.WordInfoScreenItem
import com.arsvechkarev.clich.screens.WordsListScreen
import com.arsvechkarev.clich.screens.WordsListScreen.WordsListScreenItem
import com.arsvechkarev.core.domain.dao.create
import com.arsvechkarev.testui.DatabaseHelp
import com.arsvechkarev.testui.DatabaseRule
import com.arsvechkarev.testui.doAndWait
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SelectingLabelsTest : DatabaseHelp {
  
  @get:Rule
  val chain: RuleChain = RuleChain.outerRule(ActivityTestRule(MainActivity::class.java))
    .around(DatabaseRule())
  
  @Test
  fun test1_Adding_labels_to_word() {
    doAndWait(500) {
      labels.create("Animals")
      labels.create("Pets")
      labels.create("House")
      labels.create("Other")
      
      words.create("cat", "an animal")
    }
    
    onScreen<WordsListScreen> {
      recyclerWords {
        hasSize(1)
        firstChild<WordsListScreenItem> { click() }
      }
    }
    
    onScreen<WordInfoScreen> {
      recyclerWordsLabels.hasSize(0)
      buttonAddLabeds.click()
    }
    
    onScreen<LabelsCheckBoxScreen> {
      recyclerLabels {
        hasSize(4)
        childWith<LabelsCheckBoxScreenItem> { withDescendant { withText("Animals") } } perform {
          checkbox.click()
        }
        childWith<LabelsCheckBoxScreenItem> { withDescendant { withText("Pets") } } perform {
          checkbox.click()
        }
      }
      
      pressBack()
    }
    
    onScreen<WordInfoScreen> {
      recyclerWordsLabels {
        hasSize(2)
        childWith<WordInfoScreenItem> { withDescendant { withText("Animals") } } perform { isVisible() }
        childWith<WordInfoScreenItem> { withDescendant { withText("Pets") } } perform { isVisible() }
      }
  
      buttonAddLabeds.click()
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