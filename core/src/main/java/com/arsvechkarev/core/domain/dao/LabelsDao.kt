package com.arsvechkarev.core.domain.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.arsvechkarev.core.domain.model.LabelEntity

@Dao
interface LabelsDao {
  
  @Insert
  suspend fun create(labelEntity: LabelEntity)
  
  @Update
  suspend fun update(labelEntity: LabelEntity)
  
  @Query("SELECT * FROM labels")
  fun getAllLive(): LiveData<List<LabelEntity>>
  
  @Delete
  suspend fun delete(labelEntity: LabelEntity)
  
}