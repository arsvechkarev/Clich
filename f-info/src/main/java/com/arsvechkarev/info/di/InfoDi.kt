package com.arsvechkarev.info.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.arsvechkarev.core.ListenableWordsDataSource
import com.arsvechkarev.core.di.CoreComponent
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.domain.dao.WordsLabelsDao
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.info.presentation.InfoViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
  dependencies = [CoreComponent::class],
  modules = [InfoViewModelModule::class]
)
@FeatureScope
interface InfoComponent {
  
  fun inject(fragment: InfoFragment)
  
  @Component.Builder
  interface Builder {
    
    fun coreComponent(coreComponent: CoreComponent): Builder
    
    @BindsInstance
    fun infoFragment(infoFragment: InfoFragment): Builder
    
    fun build(): InfoComponent
  }
}

@Suppress("UNCHECKED_CAST")
@Module
class InfoViewModelModule {
  
  @Provides
  @FeatureScope
  fun provideViewModel(
    infoFragment: InfoFragment,
    wordsLabelsDao: WordsLabelsDao,
    listenableWordsDataSource: ListenableWordsDataSource
  ): InfoViewModel {
    val factory = object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InfoViewModel(listenableWordsDataSource, wordsLabelsDao) as T
      }
    }
    return ViewModelProviders.of(infoFragment, factory).get(InfoViewModel::class.java)
  }
}