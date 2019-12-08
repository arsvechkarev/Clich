package com.arsvechkarev.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arsvechkarev.core.DisplayableItem
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "words")
@Parcelize
data class Word(
  @PrimaryKey(autoGenerate = true)
  override val id: Long? = null,
  var word: String,
  var definition: String? = null
) : Parcelable, DisplayableItem

fun List<Word>.words(): String {
  val sb = StringBuilder()
  forEach {
    sb.append(it.word).append(",")
  }
  return sb.toString()
}
