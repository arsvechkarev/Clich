package com.arsvechkarev.clich.tests

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.arsvechkarev.clich.MainActivity
import com.arsvechkarev.clich.screens.WordInfoScreen
import com.arsvechkarev.clich.screens.WordsListScreen
import com.arsvechkarev.clich.screens.WordsListScreen.WordItem
import com.arsvechkarev.storage.database.CentralDatabase
import com.arsvechkarev.testui.clearAndTypeText
import com.arsvechkarev.testui.screen
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
      CentralDatabase.instance.clearAllTables()
    }
  }
  
  /**
   * 1. Click to new word button
   * 2. Create word
   * 3. Check if it is displayed in the recycler
   * 4. Click on this item
   * 5. Go to info fragment and check if data displayed correctly
   */
  @Test
  fun test1_Creating_new_word_and_checking_that_it_is_displayed_after() {
    screen<WordsListScreen>().fabNewWord.click()
    
    onScreen<WordInfoScreen> {
      textNewWord.isDisplayed()
      
      editTextWord.typeText("cat")
      editTextDefinition.typeText("a small animal")
      
      imageBack.click()
    }
    
    onScreen<WordsListScreen> {
      recyclerWords {
        hasSize(1)
        firstChild<WordItem> {
          textWord.hasText("cat")
          click()
        }
      }
      
    }
    
    onScreen<WordInfoScreen> {
      textNewWord.isNotDisplayed()
      
      editTextWord.hasText("cat")
      editTextDefinition.hasText("a small animal")
      
      pressBack()
    }
  }
  
  @Test
  fun test2_Editing_a_word() {
    onScreen<WordsListScreen> {
      recyclerWords.firstChild<WordItem> {
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
        firstChild<WordItem> {
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
}
