package com.arsvechkarev.clich

import android.app.Application
import com.arsvechkarev.storage.database.CentralDatabase
import timber.log.Timber

class App : Application() {
  
  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())
    CentralDatabase.instantiate(this)
  }
}