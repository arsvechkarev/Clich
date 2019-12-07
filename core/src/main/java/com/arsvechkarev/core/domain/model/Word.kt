package com.arsvechkarev.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "words")
@Parcelize
data class Word(
  @PrimaryKey(autoGenerate = true)
  val id: Long? = null,
  var word: String,
  var definition: String? = null
) : Parcelable {
  companion object {
    fun stub(): Word {
      return Word(null, "", "")
    }
  }
  
  val isStub get() = id == null && word == "none" && definition == "none"
}

fun List<Word>.words(): String {
  val sb = StringBuilder()
  forEach {
    sb.append(it.word).append(",")
  }
  return sb.toString()
}
