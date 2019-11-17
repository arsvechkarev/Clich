package com.arsvechkarev.list.di

import com.arsvechkarev.core.di.CoreModule
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.list.presentation.WordsListFragment
import com.arsvechkarev.storage.di.StorageModule
import dagger.Component

@Component(
  modules = [
    CoreModule::class,
    WordsListViewModelModule::class,
    StorageModule::class
  ]
)
@FeatureScope
interface WordsListComponent {
  
  fun inject(fragment: WordsListFragment)
}