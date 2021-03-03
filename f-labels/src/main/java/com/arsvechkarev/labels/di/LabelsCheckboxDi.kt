package com.arsvechkarev.labels.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.arsvechkarev.core.DispatcherProvider
import com.arsvechkarev.core.di.CoreComponent
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.core.domain.dao.LabelsDao
import com.arsvechkarev.core.domain.dao.WordsLabelsDao
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.list.Mode
import com.arsvechkarev.labels.presentation.LabelCheckedCallback
import com.arsvechkarev.labels.presentation.LabelsCheckboxFragment
import com.arsvechkarev.labels.presentation.LabelsCheckboxViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
  dependencies = [CoreComponent::class],
  modules = [LabelsCheckboxModule::class]
)
@FeatureScope
interface LabelsCheckboxComponent {
  
  fun inject(fragment: LabelsCheckboxFragment)
  
  @Component.Builder
  interface Builder {
    
    fun coreComponent(coreComponent: CoreComponent): Builder
    
    @BindsInstance
    fun labelsCheckboxFragment(labelsCheckboxFragment: LabelsCheckboxFragment): Builder
    
    @BindsInstance
    fun inputWord(word: Word): Builder
    
    fun build(): LabelsCheckboxComponent
  }
}

@Suppress("UNCHECKED_CAST")
@Module
class LabelsCheckboxModule {
  
  @Provides
  @FeatureScope
  fun provideAdapter(labelsCheckboxFragment: LabelsCheckboxFragment): LabelsAdapter {
    return LabelsAdapter(Mode.Checkbox(labelsCheckboxFragment as LabelCheckedCallback))
  }
  
  @Provides
  @FeatureScope
  fun provideViewModel(
    labelsCheckboxFragment: LabelsCheckboxFragment,
    word: Word,
    labelsDao: LabelsDao,
    wordsLabelsDao: WordsLabelsDao,
    dispatcherProvider: DispatcherProvider,
  ): LabelsCheckboxViewModel {
    val factory = object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>) = LabelsCheckboxViewModel(
        word,
        labelsDao,
        wordsLabelsDao,
        dispatcherProvider
      ) as T
    }
    return ViewModelProviders.of(labelsCheckboxFragment, factory)
      .get(LabelsCheckboxViewModel::class.java)
  }
}