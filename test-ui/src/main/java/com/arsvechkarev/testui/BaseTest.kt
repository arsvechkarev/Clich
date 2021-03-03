package com.arsvechkarev.testui

import com.arsvechkarev.core.CentralDatabase

/**
 * Helper fields to work with database
 */
interface BaseTest {
  
  val database get() = CentralDatabase.instance
  
  val labelsDao get() = database.labelsDao()
  val wordsDao get() = database.wordsDao()
  val wordsLabelsDao get() = database.wordsLabelsDao()
}