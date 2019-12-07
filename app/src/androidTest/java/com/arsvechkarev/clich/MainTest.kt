package com.arsvechkarev.clich

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.arsvechkarev.clich.screens.AllLabelsScreen
import com.arsvechkarev.clich.screens.AllLabelsScreen.MainItem
import com.arsvechkarev.clich.screens.DrawerScreen
import com.arsvechkarev.clich.screens.DrawerScreen.RecyclerDrawerItem
import com.arsvechkarev.clich.screens.MainScreen
import com.arsvechkarev.clich.screens.NewLabelDialogScreen
import com.arsvechkarev.clich.screens.WordInfoScreen
import com.arsvechkarev.clich.screens.WordsListScreen
import com.arsvechkarev.clich.screens.WordsListScreen.WordItem
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainTest {
  
  @get:Rule
  val activityRule = ActivityTestRule(MainActivity::class.java)
  
  /**
   * Plan of the test:
   *
   * 1. Click to new word button
   * 2. Create word
   * 3. Check if it is displayed in the recycler
   * 4. Click on this item
   * 5. Go to info fragment and check if data displayed correctly
   */
  @Test
  fun test1_Creating_new_word_and_checking_that_it_is_displayed_after() {
    onScreen<WordsListScreen> {
      fabNewWord.click()
    }
    
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
  
  /**
   * Plan of the test:
   *
   * 1. Click to new word button
   * 2. Create word
   * 3. Check if it is displayed in the recycler
   * 4. Click on this item
   * 5. Go to info fragment and check if data displayed correctly
   */
  @Test
  fun test2_Creating_a_new_label_and_make_sure_that_it_is_displayed() {
    onScreen<MainScreen> {
      drawer.open()
    }
    
    onScreen<DrawerScreen> {
      buttonLabels.click()
    }
    
    onScreen<AllLabelsScreen> {
      fabNewLabel.click()
      
      onScreen<NewLabelDialogScreen> {
        editTextLabelName.typeText("Animals")
        buttonCreate.click()
      }
  
      recyclerLabels {
        hasSize(1)
        firstChild<MainItem> {
          textLabel.hasText("Animals")
        }
      }
      
      pressBack()
    }
  
    onScreen<MainScreen> {
      drawer.open()
    }
    
    onScreen<DrawerScreen> {
      recyclerDrawerLabels {
        hasSize(1)
        firstChild<RecyclerDrawerItem> {
          textLabel.hasText("Animals")
        }
      }
    }
  }
  
}
