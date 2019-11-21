package com.arsvechkarev.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arsvechkarev.core.domain.dao.LabelsDao
import com.arsvechkarev.core.domain.dao.WordsDao
import com.arsvechkarev.core.domain.dao.WordsLabelsDao
import com.arsvechkarev.core.domain.model.LabelEntity
import com.arsvechkarev.core.domain.model.WordEntity

@Database(entities = [
  WordEntity::class,
  LabelEntity::class,
  WordsLabelsDao::class
], version = 1)
abstract class WordsDatabase : RoomDatabase() {
  
  abstract fun wordDao(): WordsDao
  abstract fun labelsDao(): LabelsDao
  abstract fun wordsAndLabelsDao(): WordsLabelsDao
  
  companion object {
    
    lateinit var database: WordsDatabase
      private set
    
    lateinit var wordsDao: WordsDao
      private set
    
    fun instantiate(context: Context) {
      database = Room.databaseBuilder(
        context,
        WordsDatabase::class.java,
        "words.db"
      ).build()
      wordsDao = database.wordDao()
    }
  }
}
