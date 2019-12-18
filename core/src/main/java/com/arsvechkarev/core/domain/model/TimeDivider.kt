package com.arsvechkarev.core.domain.model

import com.arsvechkarev.core.FORMAT_TIME_DIVIDER
import com.arsvechkarev.core.recyler.DisplayableItem
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter.ofPattern
import java.util.UUID

data class TimeDivider(
  override val id: Long,
  val date: String
) : DisplayableItem {
  override var type = ItemTypeIds.TIME_DIVIDER
  
  companion object {
    fun of(epochDay: Long): TimeDivider {
      val formattedDate = LocalDate.ofEpochDay(epochDay).format(ofPattern(FORMAT_TIME_DIVIDER))
      return TimeDivider(UUID.randomUUID().mostSignificantBits, formattedDate)
    }
  }
}