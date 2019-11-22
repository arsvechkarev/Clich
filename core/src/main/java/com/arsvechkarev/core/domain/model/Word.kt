package com.arsvechkarev.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "words")
@Parcelize
data class Word(
  @PrimaryKey(autoGenerate = true)
  val id: Int? = null,
  var word: String,
  var definition: String
) : Parcelable