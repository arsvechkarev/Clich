package com.arsvechkarev.clich.tests

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.arsvechkarev.clich.presentation.MainActivity
import com.arsvechkarev.clich.R
import com.arsvechkarev.clich.screens.AllLabelsScreen
import com.arsvechkarev.clich.screens.AllLabelsScreen.AllLabelsScreenItem
import com.arsvechkarev.clich.screens.DrawerScreen
import com.arsvechkarev.clich.screens.MainScreen
import com.arsvechkarev.clich.screens.NewLabelDialogScreen
import com.arsvechkarev.core.domain.dao.create
import com.arsvechkarev.testui.DatabaseHelp
import com.arsvechkarev.testui.DatabaseRule
import com.arsvechkarev.testui.clearAndTypeText
import com.arsvechkarev.testui.doAndWait
import com.arsvechkarev.testui.isVisibleAndHasText
import com.arsvechkarev.testui.screen
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LabelsTest : DatabaseHelp {
  
  @get:Rule
  val chain: RuleChain = RuleChain.outerRule(ActivityTestRule(MainActivity::class.java))
    .around(DatabaseRule())
  
  @Test
  fun test1_Creating_new_label_and_make_sure_that_it_is_displayed() {
    screen<MainScreen>().drawer.open()
    screen<DrawerScreen>().buttonCreateLabel.click()
    
    onScreen<AllLabelsScreen> {
      fabNewLabel.click()
      
      onScreen<NewLabelDialogScreen> {
        editTextLabelName.typeText("Animals")
        buttonCreate.click()
      }
      
      recyclerLabels {
        hasSize(1)
        firstChild<AllLabelsScreenItem> {
          textLabel.hasText("Animals")
        }
      }
      
      pressBack()
    }
    
    screen<MainScreen>().drawer.open()
    
    onScreen<DrawerScreen> {
      recyclerDrawerLabels {
        hasSize(1)
        firstChild<DrawerScreen.DrawerScreenItem> {
          textLabel.hasText("Animals")
        }
      }
    }
  }
  
  @Test
  fun text2_Edit_label_delete_it_and_make_sure_it_is_not_displayed() {
    screen<MainScreen>().drawer.open()
    screen<DrawerScreen>().buttonCreateLabel.click()
    
    doAndWait(500) {
      database.labelsDao().create("Animals")
    }
    
    onScreen<AllLabelsScreen> {
      layoutLabelsStub.isNotDisplayed()
      
      recyclerLabels.firstChild<AllLabelsScreenItem> {
        
        imageEnd.click()
        
        dividerTop.isVisible()
        dividerBottom.isVisible()
        imageStart.hasDrawable(R.drawable.ic_delete)
        imageEnd.hasDrawable(R.drawable.ic_checkmark)
        editTextLabel.isVisible()
        textLabel.isNotDisplayed()
        
        imageEnd.click()
  
        dividerTop.isNotDisplayed()
        dividerBottom.isNotDisplayed()
        imageStart.hasDrawable(R.drawable.ic_label)
        imageEnd.hasDrawable(R.drawable.ic_edit)
        editTextLabel.isNotDisplayed()
        textLabel isVisibleAndHasText "Animals"
        
        imageEnd.click()
        
        editTextLabel clearAndTypeText "Other stuff"
        
        imageEnd.click()
        
        textLabel isVisibleAndHasText "Other stuff"
        
        imageEnd.click()
        
        imageStart.click()
      }
      
      recyclerLabels.isNotDisplayed()
      layoutLabelsStub.isVisible()
    }
  }
}