package com.arsvechkarev.clich

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.arsvechkarev.clich.screens.TheInfoScreen
import com.arsvechkarev.clich.screens.TheWordsListScreen
import com.arsvechkarev.clich.screens.TheWordsListScreen.WordItem
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainTest {
  
  @get:Rule
  val activityRule = ActivityTestRule(MainActivity::class.java)
  
  @Test
  fun creating_new_word_and_checking_whether_it_is_displayed_after_that() {
    onScreen<TheWordsListScreen> {
      fabNewWord.click()
    }
    
    onScreen<TheInfoScreen> {
      textNewWord.isDisplayed()
      
      editTextWord.typeText("cat")
      editTextDefinition.typeText("a small animal")
  
      imageBack.click()
    }
  
    onScreen<TheWordsListScreen> {
      recyclerWords {
        hasSize(1)
        firstChild<WordItem> {
          textWord.hasText("cat")
          click()
        }
      }
      
    }
  
    onScreen<TheInfoScreen> {
      textNewWord.isNotDisplayed()
    
      editTextWord.hasText("cat")
      editTextDefinition.hasText("a small animal")
    
      pressBack()
    }
    
  }
  
}