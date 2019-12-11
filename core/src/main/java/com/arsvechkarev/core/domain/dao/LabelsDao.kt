package com.arsvechkarev.core.domain.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.arsvechkarev.core.domain.model.Label

@Dao
interface LabelsDao {
  
  @Insert
  suspend fun create(label: Label): Long
  
  @Insert
  suspend fun create(label: List<Label>)
  
  @Update
  suspend fun update(label: Label)
  
  @Query("SELECT * FROM labels")
  fun getAll(): LiveData<List<Label>>
  
  @Query("SELECT * FROM labels")
  suspend fun getAllSuspend(): List<Label>
  
  @Delete
  suspend fun delete(label: Label)
}

suspend fun LabelsDao.create(name: String) = create(Label(name = name))
