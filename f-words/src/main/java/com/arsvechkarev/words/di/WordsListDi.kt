package com.arsvechkarev.info.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.arsvechkarev.core.CentralDatabase
import com.arsvechkarev.core.Consumer
import com.arsvechkarev.core.di.CoreComponent
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.words.list.WordsListAdapter
import com.arsvechkarev.words.presentation.WordsListFragment
import com.arsvechkarev.words.presentation.WordsListViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
  dependencies = [CoreComponent::class],
  modules = [WordsListViewModelModule::class]
)
@FeatureScope
interface WordsListComponent {
  
  fun inject(fragment: WordsListFragment)
  
  @Component.Builder
  interface Builder {
    
    fun coreComponent(coreComponent: CoreComponent): Builder
    
    @BindsInstance
    fun wordsListFragment(wordsListFragment: WordsListFragment): Builder
    
    @BindsInstance
    fun onWordClick(onWordClick: Consumer<Word>): Builder
    
    fun build(): WordsListComponent
  }
}

@Suppress("UNCHECKED_CAST")
@Module
class WordsListViewModelModule {
  
  @Provides
  @FeatureScope
  fun provideWordsListAdapter(onWordClick: Consumer<Word>): WordsListAdapter {
    return WordsListAdapter(clickListener = { onWordClick.accept(it) })
  }
  
  @Provides
  @FeatureScope
  fun provideViewModel(
    wordsListFragment: WordsListFragment,
    centralDatabase: CentralDatabase
  ): WordsListViewModel {
    val factory = object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WordsListViewModel(centralDatabase) as T
      }
    }
    return ViewModelProviders.of(wordsListFragment, factory).get(WordsListViewModel::class.java)
  }
}