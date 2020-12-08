package com.arsvechkarev.clich

import android.app.Application
import com.arsvechkarev.storage.CentralDatabase
import com.arsvechkarev.storage.DatabaseHolder
import com.jakewharton.threetenabp.AndroidThreeTen
import log.Logger

class App : Application() {
  
  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(applicationContext)
    Logger.activate()
    DatabaseHolder.instantiate(applicationContext)
    CentralDatabase.instantiate(applicationContext)
  }
}