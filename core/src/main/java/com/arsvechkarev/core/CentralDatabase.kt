package com.arsvechkarev.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arsvechkarev.core.domain.dao.LabelsDao
import com.arsvechkarev.core.domain.dao.WordsDao
import com.arsvechkarev.core.domain.dao.WordsLabelsDao
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.WordsLabelsJoin

@Database(
  entities = [
    Word::class,
    Label::class,
    WordsLabelsJoin::class
  ], version = 1
)
abstract class CentralDatabase : RoomDatabase() {
  
  abstract fun wordsDao(): WordsDao
  abstract fun labelsDao(): LabelsDao
  abstract fun wordsLabelsDao(): WordsLabelsDao
  
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
