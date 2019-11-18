package com.arsvechkarev.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "words")
data class WordEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int? = null,
  var word: String,
  var definition: String,
  var label: String?
) : Parcelable, Serializable

data class Word(
  var word: String,
  var definition: String,
  var label: String?
)

fun WordEntity.toWord(): Word {
  return Word(word, definition, label)
}

fun Word.toWordEntity(): WordEntity {
  return WordEntity(null, word, definition, label)
}