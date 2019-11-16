package com.arsvechkarev.storage.di

import android.content.Context
import com.arsvechkarev.core.di.ContextModule
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.storage.FileStorage
import com.arsvechkarev.storage.Storage
import dagger.Module
import dagger.Provides

@Module(includes = [ContextModule::class])
class StorageModule {
  
  @Provides
  @FeatureScope
  fun provideStorage(context: Context): Storage = FileStorage(context)
}