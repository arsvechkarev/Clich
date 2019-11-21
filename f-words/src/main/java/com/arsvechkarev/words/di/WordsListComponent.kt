package com.arsvechkarev.words.di

import com.arsvechkarev.core.di.CoreModule
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.storage.di.StorageModule
import com.arsvechkarev.words.presentation.WordsListFragment
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