package com.arsvechkarev.words.presentation

import com.arsvechkarev.core.domain.model.TimeDivider
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.recyler.DisplayableItem
import org.junit.Test

class WordsListViewModelTest {
  
  @Test
  fun test() {
    
    val words = listOf(
      Word(1, "aa", "", "", 12)
    )
    
    val list = test(words)
    
    list.forEach {
      if (it is Word) {
        println(it.name + " : " + it.creationDate)
      }
      if (it is TimeDivider) {
        println(it.date)
      }
    }
    
  }
  
  fun test(list: List<Word>): List<DisplayableItem> {
    val items = ArrayList<DisplayableItem>()
    for (i in list.indices) {
      if (i != list.size - 1) {
        items.add(list[i])
        if (list[i].creationDate < list[i + 1].creationDate) {
          items.add(TimeDivider.of(list[i].creationDate))
        }
      } else {
        items.add(list[i])
        items.add(TimeDivider.of(list[i].creationDate))
      }
    }
    return items
  }
}