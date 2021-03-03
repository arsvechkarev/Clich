package com.arsvechkarev.info.list

import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.recyler.BaseAdapter
import com.arsvechkarev.core.recyler.delegate
import com.arsvechkarev.info.R
import kotlinx.android.synthetic.main.item_current_label.view.textLabel
import javax.inject.Inject

class LabelsForWordAdapter(
  private val clickListener: (Label) -> Unit = {}
) : BaseAdapter() {
  
  @Inject
  constructor() : this({})
  
  init {
    addDelegates(
      delegate<Label> {
        layoutRes(R.layout.item_current_label)
        onInitViewHolder {
          itemView.setOnClickListener { clickListener(item) }
        }
        onBind {
          itemView.textLabel.text = item.name
        }
      }
    )
  }
}