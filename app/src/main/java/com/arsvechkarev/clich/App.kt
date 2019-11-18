package com.arsvechkarev.clich

import android.app.Application
import com.arsvechkarev.storage.database.WordsDatabase

class App : Application() {
  
  override fun onCreate() {
    super.onCreate()
    WordsDatabase.instantiate(this)
  }
}