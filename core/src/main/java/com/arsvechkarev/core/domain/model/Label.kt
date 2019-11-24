package com.arsvechkarev.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "labels")
@Parcelize
data class Label(
  @PrimaryKey(autoGenerate = true)
  val id: Int? = null,
  var name: String
) : Comparable<Label>, Parcelable {
  override fun compareTo(other: Label): Int {
    return name.compareTo(other.name)
  }
}