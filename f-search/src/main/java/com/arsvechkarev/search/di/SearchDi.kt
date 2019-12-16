package com.arsvechkarev.search.di
import androidx.lifecycle.ViewModel
import com.arsvechkarev.core.di.CoreModule
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.di.viewmodel.ViewModelKey
import com.arsvechkarev.search.presentation.SearchFragment
import com.arsvechkarev.search.presentation.SearchViewModel
import com.arsvechkarev.storage.di.StorageModule
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [CoreModule::class, StorageModule::class, SearchViewModelModule::class])
@FeatureScope
interface SearchComponent {
  
  fun inject(fragment: SearchFragment)
}

@Module
abstract class SearchViewModelModule {
  
  @Binds
  @IntoMap
  @ViewModelKey(SearchViewModel::class)
  internal abstract fun postSearchViewModel(viewModel: SearchViewModel): ViewModel
  
}