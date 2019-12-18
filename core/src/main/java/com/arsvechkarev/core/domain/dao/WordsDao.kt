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
  suspend fun create(word: Word): Long
  
  @Update
  suspend fun update(word: Word)
  
  @Delete
  suspend fun delete(word: Word)
  
  @Query("SELECT * FROM words")
  fun getAll(): LiveData<List<Word>>
  
  @Query("SELECT * FROM words WHERE words.name LIKE :input")
  fun search(input: String): LiveData<List<Word>>
}

suspend fun WordsDao.create(name: String) =
  create(Word(name = name, definition = "", examples = "", creationDate = 1))

suspend fun WordsDao.create(name: String, definition: String) =
  create(Word(name = name, definition = definition, examples = "", creationDate = 1))
  
suspend fun WordsDao.create(name: String, definition: String, examples: String) =
  create(Word(name = name, definition = definition, examples = examples, creationDate = 1))
  