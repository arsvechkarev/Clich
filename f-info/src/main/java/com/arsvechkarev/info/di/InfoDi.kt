package com.arsvechkarev.info.di

import androidx.lifecycle.ViewModel
import com.arsvechkarev.core.di.CoreModule
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.di.viewmodel.ViewModelKey
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.info.presentation.InfoViewModel
import com.arsvechkarev.storage.di.StorageModule
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [CoreModule::class, StorageModule::class, InfoViewModelModule::class])
@FeatureScope
interface InfoComponent {
  
  fun inject(fragment: InfoFragment)
}

@Module
abstract class InfoViewModelModule {
  
  @Binds
  @IntoMap
  @ViewModelKey(InfoViewModel::class)
  internal abstract fun postInfoViewModel(viewModel: InfoViewModel): ViewModel
  
}