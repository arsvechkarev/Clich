package com.arsvechkarev.core.domain.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.arsvechkarev.core.domain.model.WordEntity

@Dao
interface WordsDao {
  
  @Insert
  suspend fun create(wordEntity: WordEntity)
  
  @Update
  suspend fun update(wordEntity: WordEntity)
  
  @Query("SELECT * FROM words")
  fun getAllLive(): LiveData<List<WordEntity>>
  
  @Delete
  suspend fun delete(wordEntity: WordEntity)
}