package com.arsvechkarev.core.di

import androidx.lifecycle.ViewModelProvider
import com.arsvechkarev.core.DispatcherProvider
import com.arsvechkarev.core.di.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CoreModule {
  
  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
  
  @Module
  companion object {
    
    @JvmStatic
    @Provides
    fun provideDispatchersProvider(): DispatcherProvider = DispatcherProvider.DefaultImpl
  }
}