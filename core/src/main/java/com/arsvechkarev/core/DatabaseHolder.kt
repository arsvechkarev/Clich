package com.arsvechkarev.core

import android.content.Context
import androidx.room.Room

object DatabaseHolder {
  
  lateinit var instance: CentralDatabase
    private set
  
  fun instantiate(context: Context) {
    instance = Room.databaseBuilder(
      context,
      CentralDatabase::class.java,
      "words.db"
    ).build()
  }
}