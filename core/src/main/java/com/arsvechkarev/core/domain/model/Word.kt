package com.arsvechkarev.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "Words")
data class Word(
  @PrimaryKey(autoGenerate = true)
  var id: Int = -1,
  val word: String,
  val definition: String,
  val label: String?
) : Parcelable, Serializable {
}