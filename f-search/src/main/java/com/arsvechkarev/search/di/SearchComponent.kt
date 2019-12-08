package com.arsvechkarev.search.di

import com.arsvechkarev.core.di.CoreModule
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.search.presentation.SearchFragment
import com.arsvechkarev.storage.di.StorageModule
import dagger.Component

@Component(
  modules = [
    CoreModule::class,
    SearchViewModelModule::class,
    StorageModule::class
  ]
)
@FeatureScope
interface SearchComponent {
  
  fun inject(fragment: SearchFragment)
}