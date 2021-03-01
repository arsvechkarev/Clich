package com.arsvechkarev.clich.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.arsvechkarev.clich.presentation.MainActivity
import com.arsvechkarev.clich.presentation.MainViewModel
import com.arsvechkarev.core.CentralDatabase
import com.arsvechkarev.core.Consumer
import com.arsvechkarev.core.di.CoreComponent
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.list.Mode
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
  dependencies = [CoreComponent::class],
  modules = [MainModule::class]
)
@FeatureScope
interface MainComponent {
  
  fun inject(activity: MainActivity)
  
  @Component.Builder
  interface Builder {
    
    fun coreComponent(coreComponent: CoreComponent): Builder
    
    @BindsInstance
    fun mainActivity(mainActivity: MainActivity): Builder
    
    @BindsInstance
    fun onLabelClick(onItemClick: Consumer<Label>): Builder
    
    fun build(): MainComponent
  }
}

@Suppress("UNCHECKED_CAST")
@Module
class MainModule {
  
  @Provides
  @FeatureScope
  fun provideAdapter(onItemClick: Consumer<Label>): LabelsAdapter {
    return LabelsAdapter(Mode.Simple(clickListener = { onItemClick.accept(it) }))
  }
  
  @Provides
  @FeatureScope
  fun provideViewModel(
    mainActivity: MainActivity,
    centralDatabase: CentralDatabase
  ): MainViewModel {
    val factory = object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(centralDatabase) as T
      }
    }
    return ViewModelProviders.of(mainActivity, factory).get(MainViewModel::class.java)
  }
}