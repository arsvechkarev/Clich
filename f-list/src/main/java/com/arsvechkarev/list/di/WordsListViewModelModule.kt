package com.arsvechkarev.list.di

import androidx.lifecycle.ViewModel
import com.arsvechkarev.core.ViewModelKey
import com.arsvechkarev.list.presentation.WordsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WordsListViewModelModule {
  
  @Binds
  @IntoMap
  @ViewModelKey(WordsListViewModel::class)
  internal abstract fun postWordsListViewModel(infoViewModel: WordsListViewModel): ViewModel
  
}
  
