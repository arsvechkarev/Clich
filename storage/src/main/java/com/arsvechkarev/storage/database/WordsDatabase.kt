package com.arsvechkarev.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arsvechkarev.core.domain.dao.WordDao
import com.arsvechkarev.core.domain.model.WordEntity

@Database(entities = [WordEntity::class], version = 1)
abstract class WordsDatabase : RoomDatabase() {
  
  abstract fun wordDao(): WordDao
  
  companion object {
    
    lateinit var instance: WordsDatabase
      private set
    
    fun instantiate(context: Context) {
      instance = Room.databaseBuilder(
        context,
        WordsDatabase::class.java,
        "words.db"
      ).build()
    }
  }
}
