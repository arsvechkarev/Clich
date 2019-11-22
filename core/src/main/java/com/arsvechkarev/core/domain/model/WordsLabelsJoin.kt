package com.arsvechkarev.core.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
  tableName = "words_labels_join",
  primaryKeys = ["wordId", "labelId"],
  foreignKeys = [ForeignKey(
    entity = Word::class,
    parentColumns = ["id"],
    childColumns = ["wordId"]
  ), ForeignKey(
    entity = Label::class,
    parentColumns = ["id"],
    childColumns = ["labelId"]
  )]
)
data class WordsLabelsJoin(
  val wordId: Int,
  val labelId: Int
)