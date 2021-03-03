package com.arsvechkarev.info.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.Consumer
import com.arsvechkarev.core.DispatcherProvider
import com.arsvechkarev.core.ListenableWordsDataSource
import com.arsvechkarev.core.di.CoreComponent
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.domain.dao.WordsLabelsDao
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.words.list.WordsListAdapter
import com.arsvechkarev.words.presentation.WordsForLabelFragment
import com.arsvechkarev.words.presentation.WordsForLabelViewModel
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
interface WordsComponent {
  
  fun inject(fragment: WordsListFragment)
  
  fun inject(fragment: WordsForLabelFragment)
  
  @Component.Builder
  interface Builder {
    
    fun coreComponent(coreComponent: CoreComponent): Builder
    
    @BindsInstance
    fun fragment(fragment: BaseFragment): Builder
    
    @BindsInstance
    fun onWordClick(onWordClick: Consumer<Word>): Builder
    
    fun build(): WordsComponent
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
  fun provideWordsListViewModel(
    fragment: BaseFragment,
    listenableWordsDataSource: ListenableWordsDataSource,
    dispatcherProvider: DispatcherProvider
  ): WordsListViewModel {
    val factory = object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>) = WordsListViewModel(
        listenableWordsDataSource,
        dispatcherProvider
      ) as T
    }
    return ViewModelProviders.of(fragment, factory).get(WordsListViewModel::class.java)
  }
  
  @Provides
  @FeatureScope
  fun provideWordsForLabelViewModel(
    fragment: BaseFragment,
    wordsLabelsDao: WordsLabelsDao,
    dispatcherProvider: DispatcherProvider
  ): WordsForLabelViewModel {
    val factory = object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>) = WordsForLabelViewModel(
        wordsLabelsDao,
        dispatcherProvider
      ) as T
    }
    return ViewModelProviders.of(fragment, factory).get(WordsForLabelViewModel::class.java)
  }
}