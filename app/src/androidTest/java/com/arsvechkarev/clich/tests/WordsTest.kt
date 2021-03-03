package com.arsvechkarev.clich.tests

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.arsvechkarev.clich.presentation.MainActivity
import com.arsvechkarev.clich.screens.WordInfoScreen
import com.arsvechkarev.clich.screens.WordsListScreen
import com.arsvechkarev.clich.screens.WordsListScreen.WordsListScreenItemWord
import com.arsvechkarev.testui.BaseTest
import com.arsvechkarev.testui.DatabaseRule
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class WordsTest : BaseTest {
  
  @get:Rule
  val chain: RuleChain = RuleChain.outerRule(ActivityTestRule(MainActivity::class.java))
    .around(DatabaseRule())
  
  @Test
  fun test1_Creating_new_word_and_checking_that_it_is_displayed_after() {
    onScreen<WordsListScreen> {
      layoutNoWords.isVisible()
      fabNewWord.click()
    }
    onScreen<WordInfoScreen> {
      textNewWord.isVisible()
      imageMenu.isNotDisplayed()
      editTextWord.typeText("cat")
      editTextDefinition.typeText("a small animal")
      editTextExamples.typeText("cats are nice")
      imageBack.click()
    }
    onScreen<WordsListScreen> {
      layoutNoWords.isNotDisplayed()
      recyclerWords {
        hasSize(1)
        firstChild<WordsListScreenItemWord> {
          hasSibling { withText("cat") }
          click()
        }
      }
    }
    onScreen<WordInfoScreen> {
      editTextWord.hasText("cat")
      editTextDefinition.hasText("a small animal")
      editTextExamples.hasText("cats are nice")
      pressBack()
    }
  }
  
  @Test
  fun test2_Editing_a_word() {
    onScreen<WordsListScreen> {
      layoutNoWords.isVisible()
      fabNewWord.click()
    }
    onScreen<WordInfoScreen> {
      editTextWord.typeText("dog")
      editTextDefinition.typeText("just a dog")
      editTextExamples.typeText("dog says \"bark!\"")
      pressBack()
      pressBack()
    }
    onScreen<WordsListScreen> {
      recyclerWords {
        hasSize(1)
        hasDescendant { withText("dog") }
        firstChild<WordsListScreenItemWord> {
          hasSibling { withText("dog") }
          click()
        }
      }
    }
    onScreen<WordInfoScreen> {
      editTextWord {
        hasText("dog")
        typeText("g")
      }
      editTextDefinition.hasText("just a dog")
      editTextExamples.hasText("dog says \"bark!\"")
      pressBack()
      pressBack()
    }
    onScreen<WordsListScreen> {
      recyclerWords {
        hasSize(1)
        firstChild<WordsListScreenItemWord> {
          hasSibling { withText("dogg") }
          click()
        }
      }
    }
  }
}