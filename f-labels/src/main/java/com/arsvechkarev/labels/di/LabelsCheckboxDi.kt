package com.arsvechkarev.labels.di

import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.featurecommon.WordInfoComponent
import com.arsvechkarev.labels.list.LabelsAdapter
import com.arsvechkarev.labels.list.Mode
import com.arsvechkarev.labels.presentation.LabelCheckedCallback
import com.arsvechkarev.labels.presentation.LabelsCheckboxFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
  dependencies = [WordInfoComponent::class],
  modules = [LabelsCheckboxModule::class]
)
@FeatureScope
interface LabelsCheckboxComponent {
  
  fun inject(fragment: LabelsCheckboxFragment)
  
  @Component.Builder
  interface Builder {
    
    fun wordInfoComponent(wordInfoComponent: WordInfoComponent): Builder
    
    @BindsInstance
    fun labelsCheckboxFragment(labelsCheckboxFragment: LabelsCheckboxFragment): Builder
    
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
}