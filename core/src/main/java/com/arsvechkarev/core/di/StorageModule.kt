package com.arsvechkarev.core.di

import com.arsvechkarev.core.CentralDatabase
import com.arsvechkarev.core.DatabaseHolder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
  
  @Provides
  @Singleton
  fun provideDatabase(): CentralDatabase = DatabaseHolder.instance
}