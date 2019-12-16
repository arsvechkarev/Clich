package com.arsvechkarev.storage.di

import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.storage.CentralDatabase
import dagger.Module
import dagger.Provides

@Module
class StorageModule {
  
  @Provides
  @FeatureScope
  fun provideDatabase() = CentralDatabase.instance
}