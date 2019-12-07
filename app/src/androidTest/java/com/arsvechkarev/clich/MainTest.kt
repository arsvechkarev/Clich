package com.arsvechkarev.clich

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainTest {
  
  @get:Rule
  val activityRule = ActivityTestRule(MainActivity::class.java)
  
  @Test
  fun base() {
    onScreen<WordsListScreen2> {
      
      fabNewWord.click()
      
      
      
    }
    
  }
  
}