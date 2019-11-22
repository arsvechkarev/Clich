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
  suspend fun create(label: Label)
  
  @Update
  suspend fun update(label: Label)
  
  @Query("SELECT * FROM labels")
  fun getAllLive(): LiveData<List<Label>>
  
  @Delete
  suspend fun delete(label: Label)
  
}