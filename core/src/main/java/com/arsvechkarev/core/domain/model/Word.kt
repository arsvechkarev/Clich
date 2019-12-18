package com.arsvechkarev.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arsvechkarev.core.recyler.DisplayableItem
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "words")
@Parcelize

/**
 * @property creationDate Date of time the word was created converted to epoch day
 */
data class Word(
  @PrimaryKey(autoGenerate = true)
  override val id: Long? = null,
  var name: String,
  var definition: String,
  var examples: String,
  val creationDate: Long
) : Parcelable, DisplayableItem {
  @IgnoredOnParcel override var type = ItemTypeIds.WORD
}

fun List<Word>.words(): String {
  val sb = StringBuilder()
  forEach {
    sb.append(it.name).append(",")
  }
  return sb.toString()
}
