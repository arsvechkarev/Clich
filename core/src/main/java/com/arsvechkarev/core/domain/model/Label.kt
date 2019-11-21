package com.arsvechkarev.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "labels")
data class LabelEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int? = null,
  val name: String
)