package com.arsvechkarev.testunit

import com.arsvechkarev.core.Storage
import com.arsvechkarev.core.domain.model.Word

@Suppress("UNCHECKED_CAST")
class FakeWordsListStorage : Storage {
  
  private var list: MutableList<Word>? = null
  
  override suspend fun <T> save(value: T, filename: String) {
    list = value as MutableList<Word>?
  }
  
  override suspend fun <T> get(filename: String): T? {
    return list as T?
  }
  
  override suspend fun delete(filename: String) {
    list = null
  }
  
}