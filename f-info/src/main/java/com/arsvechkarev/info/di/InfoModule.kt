package com.arsvechkarev.info.di

class InfoModule

/*

package com.arsvechkarev.searchword.di

import android.content.Context
import com.arsvechkarev.searchword.domain.SearchWordApiService
import com.arsvechkarev.storage.FileStorage
import com.arsvechkarev.storage.Storage
import com.arsvechkarev.texttospeech.TextToSpeech
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class SearchWordModule {
  
  @Provides
  fun provideStorage(context: Context): Storage {
    return FileStorage(context)
  }
  
  @Provides
  fun provideTextToSpeech(context: Context): TextToSpeech {
    return TextToSpeech(context)
  }
  
  @Provides
  fun provideWordInfoApiService(retrofit: Retrofit): SearchWordApiService {
    return retrofit.create(SearchWordApiService::class.java)
  }


@Module
abstract class SearchWordViewModelModule {
  
  @Binds
  @IntoMap
  @ViewModelKey(SearchWordViewModel::class)
  internal abstract fun postSearchWordViewModel(viewModelSearch: SearchWordViewModel): ViewModel
  
}



*/