package com.arsvechkarev.core.di

import android.content.Context
import com.arsvechkarev.core.CentralDatabase
import com.arsvechkarev.core.DispatcherProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class, StorageModule::class])
interface CoreComponent {
  
  fun getContext(): Context
  
  fun getDispatcherProvider(): DispatcherProvider
  
  fun getCentralDatabase(): CentralDatabase
}