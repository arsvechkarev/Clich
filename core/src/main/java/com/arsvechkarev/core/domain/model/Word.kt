package com.arsvechkarev.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "words")
data class WordEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int? = null,
  var word: String,
  var definition: String,
  var label: String?
)

@Parcelize
data class Word(
  val id: Int? = null,
  var word: String,
  var definition: String,
  var label: String?
): Parcelable

fun WordEntity.toWord(): Word {
  return Word(id, word, definition, label)
}

fun Word.toWordEntity(): WordEntity {
  return WordEntity(id, word, definition, label)
}