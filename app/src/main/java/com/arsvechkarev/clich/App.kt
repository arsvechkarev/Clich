package com.arsvechkarev.clich

import android.app.Application
import com.arsvechkarev.storage.database.CentralDatabase
import log.Logger

class App : Application() {
  
  override fun onCreate() {
    super.onCreate()
    Logger.activate()
    CentralDatabase.instantiate(this)
  }
}