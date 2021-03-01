package com.arsvechkarev.info.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.arsvechkarev.core.CentralDatabase
import com.arsvechkarev.core.Consumer
import com.arsvechkarev.core.di.CoreComponent
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.search.labels.WordsListAdapter
import com.arsvechkarev.search.presentation.SearchFragment
import com.arsvechkarev.search.presentation.SearchViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
  dependencies = [CoreComponent::class],
  modules = [SearchViewModelModule::class]
)
@FeatureScope
interface SearchComponent {
  
  fun inject(fragment: SearchFragment)
  
  @Component.Builder
  interface Builder {
    
    fun coreComponent(coreComponent: CoreComponent): Builder
    
    @BindsInstance
    fun searchFragment(searchFragment: SearchFragment): Builder
    
    @BindsInstance
    fun onWordClick(onWordClick: Consumer<Word>): Builder
    
    fun build(): SearchComponent
  }
}

@Suppress("UNCHECKED_CAST")
@Module
class SearchViewModelModule {
  
  @Provides
  @FeatureScope
  fun provideWordsListAdapter(onWordClick: Consumer<Word>): WordsListAdapter {
    return WordsListAdapter(clickListener = { word -> onWordClick.accept(word) })
  }
  
  @Provides
  @FeatureScope
  fun provideViewModel(
    searchFragment: SearchFragment,
    centralDatabase: CentralDatabase
  ): SearchViewModel {
    val factory = object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(centralDatabase) as T
      }
    }
    return ViewModelProviders.of(searchFragment, factory).get(SearchViewModel::class.java)
  }
}