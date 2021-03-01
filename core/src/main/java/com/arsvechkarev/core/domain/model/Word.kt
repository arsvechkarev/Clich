package com.arsvechkarev.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arsvechkarev.core.recyler.DifferentiableItem
import kotlinx.android.parcel.Parcelize

/**
 * @property creationDate Date of time the word was created converted to epoch day
 */
@Entity(tableName = "words")
@Parcelize
data class Word(
  @PrimaryKey(autoGenerate = true)
  override val id: Long? = null,
  var name: String,
  var definition: String,
  var examples: String,
  val creationDate: Long
) : Parcelable, DifferentiableItem