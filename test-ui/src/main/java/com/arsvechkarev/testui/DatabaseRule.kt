package com.arsvechkarev.testui

import com.arsvechkarev.core.CentralDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class DatabaseRule : TestRule {
  
  override fun apply(base: Statement, description: Description?): Statement {
    return object : Statement() {
      override fun evaluate() {
        try {
          base.evaluate()
        } finally {
          GlobalScope.launch(Dispatchers.IO) {
            CentralDatabase.instance.clearAllTables()
          }
        }
      }
    }
  }
}