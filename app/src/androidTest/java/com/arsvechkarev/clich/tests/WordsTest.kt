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
import com.arsvechkarev.clich.MainActivity
import com.arsvechkarev.clich.R
import com.arsvechkarev.clich.screens.WordInfoScreen
import com.arsvechkarev.clich.screens.WordsListScreen
import com.arsvechkarev.clich.screens.WordsListScreen.WordsListScreenItem
import com.arsvechkarev.core.extensions.inBackground
import com.arsvechkarev.storage.database.CentralDatabase
import com.arsvechkarev.testui.clearAndTypeText
import org.junit.AfterClass
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class WordsTest {
  
  @get:Rule
  val activityRule = ActivityTestRule(MainActivity::class.java)
  
  companion object {
    @AfterClass
    fun tearDown() {
      inBackground {
        CentralDatabase.instance.clearAllTables()
      }
    }
  }
  
  @Test
  fun test1_Creating_new_word_and_checking_that_it_is_displayed_after() {
    onScreen<WordsListScreen> {
      layoutStub.isDisplayed()
      fabNewWord.click()
    }
    
    onScreen<WordInfoScreen> {
      textNewWord.isDisplayed()
      imageMenu.isNotDisplayed()
      
      editTextWord.typeText("cat")
      editTextDefinition.typeText("a small animal")
      
      imageBack.click()
    }
    
    onScreen<WordsListScreen> {
      layoutStub.isNotDisplayed()
      
      recyclerWords {
        hasSize(1)
        firstChild<WordsListScreenItem> {
          textWord.hasText("cat")
          click()
        }
      }
      
    }
    
    onScreen<WordInfoScreen> {
      imageMenu.isDisplayed()
      textNewWord.isNotDisplayed()
      
      editTextWord.hasText("cat")
      editTextDefinition.hasText("a small animal")
      
      pressBack()
    }
  }
  
  @Test
  fun test2_Editing_a_word() {
    onScreen<WordsListScreen> {
      recyclerWords.firstChild<WordsListScreenItem> {
        textWord.hasText("cat")
        click()
      }
    }
    
    onScreen<WordInfoScreen> {
      editTextWord clearAndTypeText "dog"
      editTextDefinition clearAndTypeText "just a dog"
      
      pressBack()
      pressBack()
    }
    
    onScreen<WordsListScreen> {
      recyclerWords {
        hasSize(1)
        firstChild<WordsListScreenItem> {
          textWord.hasText("dog")
          click()
        }
      }
    }
    
    onScreen<WordInfoScreen> {
      editTextWord.hasText("dog")
      editTextDefinition.hasText("just a dog")
    }
  }
  
  @Test
  fun text3_Deleting_a_word() {
    onScreen<WordsListScreen> {
      recyclerWords {
        firstChild<WordsListScreenItem> {
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
      layoutStub.isDisplayed()
      recyclerWords.isNotDisplayed()
    }
  }
}
