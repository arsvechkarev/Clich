package com.arsvechkarev.labels.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.arsvechkarev.core.DispatcherProvider
import com.arsvechkarev.core.di.CoreComponent
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.domain.dao.LabelsDao
import com.arsvechkarev.core.domain.dao.WordsLabelsDao
import com.arsvechkarev.labels.list.LabelEditingCallback
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.list.Mode
import com.arsvechkarev.labels.presentation.LabelsFragment
import com.arsvechkarev.labels.presentation.LabelsViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
  dependencies = [CoreComponent::class],
  modules = [LabelsModule::class]
)
@FeatureScope
interface LabelsComponent {
  
  fun inject(fragment: LabelsFragment)
  
  @Component.Builder
  interface Builder {
    
    fun coreComponent(coreComponent: CoreComponent): Builder
    
    @BindsInstance
    fun labelsFragment(fragment: LabelsFragment): Builder
    
    fun build(): LabelsComponent
  }
}

@Suppress("UNCHECKED_CAST")
@Module
class LabelsModule {
  
  @Provides
  @FeatureScope
  fun provideLabelsAdapter(labelsFragment: LabelsFragment): LabelsAdapter {
    return LabelsAdapter(Mode.Default(labelsFragment as LabelEditingCallback))
  }
  
  @Provides
  @FeatureScope
  fun provideViewModel(
    labelsFragment: LabelsFragment,
    labelsDao: LabelsDao,
    wordsLabelsDao: WordsLabelsDao,
    dispatcherProvider: DispatcherProvider
  ): LabelsViewModel {
    val factory = object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>) = LabelsViewModel(
        labelsDao,
        wordsLabelsDao,
        dispatcherProvider
      ) as T
    }
    return ViewModelProviders.of(labelsFragment, factory).get(LabelsViewModel::class.java)
  }
}