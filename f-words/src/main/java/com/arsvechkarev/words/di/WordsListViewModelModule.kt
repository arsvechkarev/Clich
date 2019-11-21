package com.arsvechkarev.words.di

import androidx.lifecycle.ViewModel
import com.arsvechkarev.core.di.viewmodel.ViewModelKey
import com.arsvechkarev.words.presentation.WordsListViewModel
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
