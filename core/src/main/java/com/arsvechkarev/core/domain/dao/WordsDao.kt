package com.arsvechkarev.core.domain.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.arsvechkarev.core.domain.model.Word

@Dao
interface WordsDao {
  
  @Insert
  suspend fun create(word: Word)
  
  @Update
  suspend fun update(word: Word)
  
  @Query("SELECT * FROM words")
  fun getAll(): LiveData<List<Word>>
  
  @Delete
  suspend fun delete(word: Word)
}