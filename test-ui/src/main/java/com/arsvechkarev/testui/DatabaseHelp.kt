package com.arsvechkarev.testui

import com.arsvechkarev.core.CentralDatabase

/**
 * Helper fields to work with database
 */
interface DatabaseHelp {
  
  val database get() = CentralDatabase.instance
  
  val labels get() = database.labelsDao()
  val words get() = database.wordDao()
  val wordsAndLabels get() = database.wordsAndLabelsDao()
}