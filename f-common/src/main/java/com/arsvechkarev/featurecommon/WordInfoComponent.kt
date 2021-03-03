package com.arsvechkarev.featurecommon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.arsvechkarev.core.BaseFragment
import com.arsvechkarev.core.ClichApplication
import com.arsvechkarev.core.DispatcherProvider
import com.arsvechkarev.core.ListenableWordsDataSource
import com.arsvechkarev.core.di.CoreComponent
import com.arsvechkarev.core.domain.dao.LabelsDao
import com.arsvechkarev.core.domain.dao.WordsLabelsDao
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@WordsInfoScope
@Component(
  dependencies = [CoreComponent::class],
  modules = [WordInfoViewModelModule::class]
)
interface WordInfoComponent {
  
  fun wordInfoViewModel(): WordInfoViewModel
  
  @Component.Builder
  interface Builder {
    
    fun coreComponent(coreComponent: CoreComponent): Builder
    
    @BindsInstance
    fun fragment(fragment: BaseFragment): Builder
    
    fun build(): WordInfoComponent
  }
}

object WordInfoComponentHolder {
  
  private var _wordInfoComponent: WordInfoComponent? = null
  val wordInfoComponent: WordInfoComponent get() = _wordInfoComponent!!
  
  fun create(fragment: BaseFragment) {
    _wordInfoComponent = DaggerWordInfoComponent.builder()
      .coreComponent(ClichApplication.coreComponent)
      .fragment(fragment)
      .build()
  }
  
  fun clear() {
    _wordInfoComponent = null
  }
}

@Suppress("UNCHECKED_CAST")
@Module
class WordInfoViewModelModule {
  
  @Provides
  @WordsInfoScope
  fun provideViewModel(
    fragment: BaseFragment,
    listenableWordsDataSource: ListenableWordsDataSource,
    labelsDao: LabelsDao,
    wordsLabelsDao: WordsLabelsDao,
    dispatcherProvider: DispatcherProvider
  ): WordInfoViewModel {
    val factory = object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>) = WordInfoViewModel(
        listenableWordsDataSource,
        labelsDao,
        wordsLabelsDao,
        dispatcherProvider
      ) as T
    }
    return ViewModelProviders.of(fragment, factory).get(WordInfoViewModel::class.java)
  }
}