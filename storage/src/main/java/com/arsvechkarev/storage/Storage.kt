package com.arsvechkarev.storage

/**
 * Represents a storage that can manipulate some data
 */
interface Storage {
  
  /**
   * Save value to the file with name [filename]
   */
  suspend fun <T> save(value: T, filename: String)
  
  /**
   * Returns something by given [filename] (or null if it not exists)
   */
  suspend fun <T> get(filename: String): T?
  
  /**
   * Deletes something by [filename]
   */
  suspend fun delete(filename: String)
}
