package com.arsvechkarev.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arsvechkarev.core.recyler.DisplayableItem
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "labels")
@Parcelize
data class Label(
  @PrimaryKey(autoGenerate = true)
  override val id: Long? = null,
  var name: String
) : DisplayableItem, Comparable<Label>, Parcelable {
  
  @IgnoredOnParcel override var type = ItemTypeIds.LABEL
  
  override fun compareTo(other: Label): Int {
    return name.compareTo(other.name)
  }
}