package com.arsvechkarev.core.di

import android.content.Context
import com.arsvechkarev.core.DispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule(private val context: Context) {
  
  @Provides
  @Singleton
  fun provideContext(): Context = context
  
  @Provides
  @Singleton
  fun provideDispatchersProvider(): DispatcherProvider = DispatcherProvider.DefaultImpl
}