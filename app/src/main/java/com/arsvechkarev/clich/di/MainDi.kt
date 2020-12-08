package com.arsvechkarev.clich.di

import androidx.lifecycle.ViewModel
import com.arsvechkarev.clich.presentation.MainActivity
import com.arsvechkarev.clich.presentation.MainViewModel
import com.arsvechkarev.core.di.CoreModule
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.di.viewmodel.ViewModelKey
import com.arsvechkarev.storage.di.StorageModule
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [CoreModule::class, StorageModule::class, MainViewModelModule::class])
@FeatureScope
interface MainComponent {
  
  fun inject(activity: MainActivity)
}

@Module
abstract class MainViewModelModule {
  
  @Binds
  @IntoMap
  @ViewModelKey(MainViewModel::class)
  internal abstract fun postMainViewModel(viewModel: MainViewModel): ViewModel
}