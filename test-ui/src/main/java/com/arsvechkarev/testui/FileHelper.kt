package com.arsvechkarev.testui

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import java.io.ObjectOutputStream

/**
 * Helper to work with files during instrumentation tests
 */
object FileHelper {
  
  fun <T> write(filename: String, value: T) {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    ObjectOutputStream(context.openFileOutput(filename, Context.MODE_PRIVATE)).use {
      it.writeObject(value)
    }
  }
  
  fun delete(filename: String) {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    context.deleteFile(filename)
  }
  
}