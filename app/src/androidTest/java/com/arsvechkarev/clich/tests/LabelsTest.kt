package com.arsvechkarev.clich.tests

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.agoda.kakao.screen.Screen
import com.arsvechkarev.clich.screens.AllLabelsScreen
import com.arsvechkarev.clich.screens.DrawerScreen
import com.arsvechkarev.clich.screens.MainScreen
import com.arsvechkarev.clich.screens.NewLabelDialogScreen
import com.arsvechkarev.testui.onScreen
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LabelsTest {
  
  /**
   * 1. Open drawer
   * 2. Click to all labels button
   * 3. Create a new label
   * 4. Make sure it is displayed in labels recycler
   * 5. Make sure it is displayed in drawer recycler
   */
  @Test
  fun test1_Creating_new_label_and_make_sure_that_it_is_displayed() {
    onScreen<MainScreen>().drawer.open()
    onScreen<DrawerScreen>().buttonLabels.click()
    
    Screen.onScreen<AllLabelsScreen> {
      fabNewLabel.click()
      
      Screen.onScreen<NewLabelDialogScreen> {
        editTextLabelName.typeText("Animals")
        buttonCreate.click()
      }
      
      recyclerLabels {
        hasSize(1)
        firstChild<AllLabelsScreen.MainItem> {
          textLabel.hasText("Animals")
        }
      }
      
      pressBack()
    }
    
    onScreen<MainScreen>().drawer.open()
    
    Screen.onScreen<DrawerScreen> {
      recyclerDrawerLabels {
        hasSize(1)
        firstChild<DrawerScreen.RecyclerDrawerItem> {
          textLabel.hasText("Animals")
        }
      }
    }
  }
}