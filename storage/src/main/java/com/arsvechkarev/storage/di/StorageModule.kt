package com.arsvechkarev.storage.di

import android.content.Context
import com.arsvechkarev.core.Storage
import com.arsvechkarev.core.di.ContextModule
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.storage.database.CentralDatabase
import com.arsvechkarev.storage.files.FileStorage
import dagger.Module
import dagger.Provides

@Module(includes = [ContextModule::class])
class StorageModule {
  
  @Provides
  @FeatureScope
  fun provideStorage(context: Context): Storage =
    FileStorage(context)
  
  @Provides
  @FeatureScope
  fun provideWordsDatabase(): CentralDatabase = CentralDatabase.instance
}