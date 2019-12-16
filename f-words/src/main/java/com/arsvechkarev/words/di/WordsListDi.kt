package com.arsvechkarev.words.di

import androidx.lifecycle.ViewModel
import com.arsvechkarev.core.di.CoreModule
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.di.viewmodel.ViewModelKey
import com.arsvechkarev.storage.di.StorageModule
import com.arsvechkarev.words.presentation.WordsListFragment
import com.arsvechkarev.words.presentation.WordsListViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [CoreModule::class, StorageModule::class, WordsListViewModelModule::class])
@FeatureScope
interface WordsListComponent {
  
  fun inject(fragment: WordsListFragment)
}

@Module
abstract class WordsListViewModelModule {
  
  @Binds
  @IntoMap
  @ViewModelKey(WordsListViewModel::class)
  internal abstract fun postWordsListViewModel(viewModel: WordsListViewModel): ViewModel
  
}