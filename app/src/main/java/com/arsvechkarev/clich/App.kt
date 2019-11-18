package com.arsvechkarev.clich

import android.app.Application
import com.arsvechkarev.core.WordsDatabase


class App : Application() {
  
  override fun onCreate() {
    super.onCreate()
    WordsDatabase.instantiate(this)
  }
}