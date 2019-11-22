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
import com.arsvechkarev.core.domain.model.WordsLabelsJoin

@Database(entities = [
  WordEntity::class,
  LabelEntity::class,
  WordsLabelsJoin::class
], version = 1)
abstract class CentralDatabase : RoomDatabase() {
  
  abstract fun wordDao(): WordsDao
  abstract fun labelsDao(): LabelsDao
  abstract fun wordsAndLabelsDao(): WordsLabelsDao
  
  companion object {
    
    lateinit var instance: CentralDatabase
      private set
    
    fun instantiate(context: Context) {
      instance = Room.databaseBuilder(
        context,
        CentralDatabase::class.java,
        "words.db"
      ).build()
    }
  }
}
