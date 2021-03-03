package com.arsvechkarev.info.di

import com.arsvechkarev.core.di.CoreComponent
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.featurecommon.WordInfoComponent
import com.arsvechkarev.info.presentation.InfoFragment
import dagger.Component

@Component(
  dependencies = [WordInfoComponent::class],
)
@FeatureScope
interface InfoComponent {
  
  fun inject(fragment: InfoFragment)
  
  @Component.Builder
  interface Builder {
    
    fun wordsInfoComponent(wordInfoComponent: WordInfoComponent): Builder
    
    fun build(): InfoComponent
  }
}