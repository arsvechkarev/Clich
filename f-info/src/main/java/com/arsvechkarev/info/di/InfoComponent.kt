package com.arsvechkarev.info.di

import com.arsvechkarev.core.di.CoreModule
import com.arsvechkarev.core.di.FeatureScope
import com.arsvechkarev.info.presentation.InfoFragment
import com.arsvechkarev.storage.di.StorageModule
import dagger.Component

@Component(
  modules = [
    CoreModule::class,
    StorageModule::class
  ]
)
@FeatureScope
interface InfoComponent {
  
  fun inject(fragment: InfoFragment)
}