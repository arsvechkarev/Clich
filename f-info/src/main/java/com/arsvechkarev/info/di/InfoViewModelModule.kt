package com.arsvechkarev.info.di

import androidx.lifecycle.ViewModel
import com.arsvechkarev.core.di.viewmodel.ViewModelKey
import com.arsvechkarev.info.presentation.InfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class InfoViewModelModule {
  
  @Binds
  @IntoMap
  @ViewModelKey(InfoViewModel::class)
  internal abstract fun postInfoViewModel(infoViewModel: InfoViewModel): ViewModel
  
}
  
