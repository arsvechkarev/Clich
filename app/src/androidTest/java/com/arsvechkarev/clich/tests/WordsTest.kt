package com.arsvechkarev.clich.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.arsvechkarev.clich.presentation.MainActivity
import com.arsvechkarev.clich.R
import com.arsvechkarev.clich.screens.WordInfoScreen
import com.arsvechkarev.clich.screens.WordsListScreen
import com.arsvechkarev.clich.screens.WordsListScreen.WordsListScreenItemWord
import com.arsvechkarev.core.domain.dao.create
import com.arsvechkarev.testui.DatabaseHelp
import com.arsvechkarev.testui.DatabaseRule
import com.arsvechkarev.testui.clearAndTypeText
import com.arsvechkarev.testui.doAndWait
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class WordsTest : DatabaseHelp {
  
  @get:Rule
  val chain: RuleChain = RuleChain.outerRule(ActivityTestRule(MainActivity::class.java))
    .around(DatabaseRule())
  
  @Test
  fun test1_Creating_new_word_and_checking_that_it_is_displayed_after() {
    onScreen<WordsListScreen> {
      layoutStub.isVisible()
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
      layoutStub.isNotDisplayed()
      
      recyclerWords {
        hasSize(2)
        childWith<WordsListScreenItemWord> { withDescendant { withText("cat") } } perform {
          textWord.hasText("cat")
          click()
        }
      }
      
    }
    
    onScreen<WordInfoScreen> {
      imageMenu.isVisible()
      textNewWord.isNotDisplayed()
      
      editTextWord.hasText("cat")
      editTextDefinition.hasText("a small animal")
      editTextExamples.hasText("cats are nice")
  
      pressBack()
    }
  }
  
  @Test
  fun test2_Editing_a_word() {
    
    doAndWait(500) {
      words.create("cat", "a small animal", "cats are nice")
    }
    
    onScreen<WordsListScreen> {
      recyclerWords {
        childWith<WordsListScreenItemWord> { withDescendant { withText("cat") } } perform { click() }
      }
    }
    
    onScreen<WordInfoScreen> {
      editTextWord clearAndTypeText "dog"
      editTextDefinition clearAndTypeText "just a dog"
      editTextExamples clearAndTypeText "dog says \"bark!\""
      
      pressBack()
      pressBack()
    }
    
    onScreen<WordsListScreen> {
      recyclerWords {
        hasSize(2)
        childWith<WordsListScreenItemWord> { withDescendant { withText("dog") } } perform { click() }
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
        hasSize(2)
        childWith<WordsListScreenItemWord> { withDescendant { withText("dogg") } } perform { isDisplayed() }
      }
    }
  }
  
  fun text3_Deleting_a_word() {
    onScreen<WordsListScreen> {
      recyclerWords {
        firstChild<WordsListScreenItemWord> {
          click()
        }
      }
    }
    
    onScreen<WordInfoScreen> {
      imageBack.click()
      
      // TODO (08.12.2019): Figure out how to click on menu
      openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
      onView(withText(R.string.text_delete_word)).inRoot(isPlatformPopup()).perform(click())
    }
    
    onScreen<WordsListScreen> {
      layoutStub.isVisible()
      recyclerWords.isNotDisplayed()
    }
  }
}
