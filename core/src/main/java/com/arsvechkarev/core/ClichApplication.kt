package com.arsvechkarev.core

import android.app.Application
import com.arsvechkarev.core.di.CoreComponent
import com.arsvechkarev.core.di.CoreModule
import com.arsvechkarev.core.di.DaggerCoreComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import log.Logger

class ClichApplication : Application() {
  
  override fun onCreate() {
    super.onCreate()
    Logger.activate()
    AndroidThreeTen.init(applicationContext)
    DatabaseHolder.instantiate(applicationContext)
    CentralDatabase.instantiate(applicationContext)
    _coreComponent = DaggerCoreComponent.builder()
      .coreModule(CoreModule(applicationContext))
      .build()
  }
  
  companion object {
    
    private var _coreComponent: CoreComponent? = null
    val coreComponent: CoreComponent get() = _coreComponent!!
  }
}