package com.arsvechkarev.storage

import android.content.Context
import com.arsvechkarev.core.DispatcherProvider
import kotlinx.coroutines.withContext
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileStorage(
  private val context: Context,
  private val provider: DispatcherProvider = DispatcherProvider.DefaultImpl
) : Storage {
  
  override suspend fun <T> save(value: T, filename: String) = withContext(provider.IO) {
    ObjectOutputStream(context.openFileOutput(filename, Context.MODE_PRIVATE)).use {
      it.writeObject(value)
    }
  }
  
  @Suppress("UNCHECKED_CAST")
  override suspend fun <T> get(filename: String): T? = withContext(provider.IO) {
    val file = context.getFileStreamPath(filename)
    if (file.exists()) {
      ObjectInputStream(FileInputStream(file)).use {
        return@withContext it.readObject() as? T
      }
    }
    return@withContext null
  }
  
  override suspend fun delete(filename: String) = withContext(provider.IO) {
    context.deleteFile(filename)
    return@withContext
  }
}