package com.arsvechkarev.test

import com.arsvechkarev.core.Storage

/**
 * Fake storage for tests
 */
class FakeEmptyStorage : Storage {
  
  override suspend fun <T> save(value: T, filename: String) {
  
  }
  
  override suspend fun <T> get(filename: String): T? {
    return null
  }
  
  override suspend fun delete(filename: String) {
  
  }
  
}
