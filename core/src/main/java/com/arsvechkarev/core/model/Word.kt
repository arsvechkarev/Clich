package com.arsvechkarev.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Word(
  val word: String,
  val definition: String,
  val label: String?
) : Parcelable, Serializable