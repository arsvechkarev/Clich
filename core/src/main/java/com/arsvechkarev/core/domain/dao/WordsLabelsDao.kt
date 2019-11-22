package com.arsvechkarev.core.domain.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.WordsLabelsJoin

@Dao
interface WordsLabelsDao {
  
  @Insert
  fun insert(wordsLabelsJoin: WordsLabelsJoin)
  
  @Query(
    """
           SELECT * FROM labels
           INNER JOIN words_labels_join
           ON labels.id=words_labels_join.labelId
           WHERE words_labels_join.wordId=:wordId
           """
  )
  fun getLabelsForWord(wordId: Int): LiveData<List<Label>>
  
  @Query(
    """
           SELECT * FROM words
           INNER JOIN words_labels_join
           ON words.id=words_labels_join.wordId
           WHERE words_labels_join.labelId=:labelId
           """
  )
  fun getWordsOfLabel(labelId: Int): LiveData<List<Word>>
}