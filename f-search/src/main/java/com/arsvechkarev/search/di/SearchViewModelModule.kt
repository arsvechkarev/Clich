package com.arsvechkarev.search.di

import androidx.lifecycle.ViewModel
import com.arsvechkarev.core.di.viewmodel.ViewModelKey
import com.arsvechkarev.search.presentation.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchViewModelModule {
  
  @Binds
  @IntoMap
  @ViewModelKey(SearchViewModel::class)
  internal abstract fun postSearchViewModel(searchViewModel: SearchViewModel): ViewModel
  
}
