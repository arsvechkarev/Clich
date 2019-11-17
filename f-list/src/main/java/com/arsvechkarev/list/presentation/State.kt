package com.arsvechkarev.list.presentation

import com.arsvechkarev.core.model.Word

/**
 * Represents state of retrieving list
 */
sealed class State {
  
  /** Result is successful, [list] is not empty */
  class Success(val list: List<Word>) : State()
  
  /** Something went wrong */
  class Failure(val error: Throwable) : State()
  
  /** Result is empty */
  object Empty : State()
}