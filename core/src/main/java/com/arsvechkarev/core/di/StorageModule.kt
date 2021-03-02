package com.arsvechkarev.core.di

import com.arsvechkarev.core.CentralDatabase
import com.arsvechkarev.core.DatabaseHolder
import com.arsvechkarev.core.DispatcherProvider
import com.arsvechkarev.core.ListenableWordsDataSource
import com.arsvechkarev.core.domain.dao.WordsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
  
  @Provides
  @Singleton
  fun provideDatabase(): CentralDatabase = DatabaseHolder.instance
  
  @Provides
  @Singleton
  fun provideWordsDao(database: CentralDatabase) = database.wordsDao()
  
  @Provides
  @Singleton
  fun provideLabelsDao(database: CentralDatabase) = database.labelsDao()
  
  @Provides
  @Singleton
  fun provideWordsLabelsDao(database: CentralDatabase) = database.wordsLabelsDao()
  
  @Provides
  @Singleton
  fun provideListenableWordsDataSource(
    wordsDao: WordsDao,
    dispatcherProvider: DispatcherProvider
  ): ListenableWordsDataSource {
    return ListenableWordsDataSource(wordsDao, dispatcherProvider)
  }
}