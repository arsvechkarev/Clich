package com.arsvechkarev.words.list

import com.arsvechkarev.core.domain.model.ItemTypeIds
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.recyler.BaseListAdapter

class WordsListAdapter(
  clickListener: (Word) -> Unit = {}
) : BaseListAdapter() {
  
  init {
    delegates.put(ItemTypeIds.WORD, WordAdapterDelegate(clickListener))
  }
  
}