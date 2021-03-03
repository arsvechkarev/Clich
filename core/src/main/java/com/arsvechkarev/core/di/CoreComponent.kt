package com.arsvechkarev.core.di

import android.content.Context
import com.arsvechkarev.core.CentralDatabase
import com.arsvechkarev.core.DispatcherProvider
import com.arsvechkarev.core.ListenableWordsDataSource
import com.arsvechkarev.core.domain.dao.LabelsDao
import com.arsvechkarev.core.domain.dao.WordsDao
import com.arsvechkarev.core.domain.dao.WordsLabelsDao
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class, StorageModule::class])
interface CoreComponent {
  
  fun getContext(): Context
  
  fun getDispatcherProvider(): DispatcherProvider
  
  fun getCentralDatabase(): CentralDatabase
  
  fun getListenableWordsDataSource(): ListenableWordsDataSource
  
  fun getWordsDao(): WordsDao
  
  fun getLabelsDao(): LabelsDao
  
  fun getWordsLabelsDao(): WordsLabelsDao
}