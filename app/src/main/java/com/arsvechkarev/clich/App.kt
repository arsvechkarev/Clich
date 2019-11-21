package com.arsvechkarev.clich

import android.app.Application
import com.arsvechkarev.storage.database.CentralDatabase

class App : Application() {
  
  override fun onCreate() {
    super.onCreate()
    CentralDatabase.instantiate(this)
  }
}