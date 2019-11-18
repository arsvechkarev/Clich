package com.arsvechkarev.core;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.arsvechkarev.core.domain.dao.WordDao;
import com.arsvechkarev.core.domain.model.Word;

@Database(
    entities = {Word.class},
    version = 1
)
public abstract class WordsDatabase extends RoomDatabase{

  public abstract WordDao wordDao();

  private static WordsDatabase instance;

  public static void instantiate(Context context) {
    instance = Room.databaseBuilder(
        context,
        WordsDatabase.class,
        "words.db"
    ).build();
  }


  public static WordsDatabase getInstance() {
    return instance;
  }
}
