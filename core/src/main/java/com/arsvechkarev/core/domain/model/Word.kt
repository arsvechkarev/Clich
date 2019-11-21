package com.arsvechkarev.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "words")
data class WordEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int? = null,
  var word: String,
  var definition: String
)

@Parcelize
data class Word(
  val id: Int? = null,
  var word: String,
  var definition: String
) : Parcelable

fun WordEntity.toWord(): Word {
  return Word(id, word, definition)
}

fun Word.toWordEntity(): WordEntity {
  return WordEntity(id, word, definition)
}