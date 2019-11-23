package com.arsvechkarev.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "labels")
data class Label(
  @PrimaryKey(autoGenerate = true)
  val id: Int? = null,
  var name: String
) : Comparable<Label> {
  override fun compareTo(other: Label): Int {
    return name.compareTo(other.name)
  }
}