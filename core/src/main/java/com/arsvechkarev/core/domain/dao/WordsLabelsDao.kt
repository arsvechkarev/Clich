package com.arsvechkarev.core.domain.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.arsvechkarev.core.domain.model.Label
import com.arsvechkarev.core.domain.model.Word
import com.arsvechkarev.core.domain.model.WordsLabelsJoin

@Dao
interface WordsLabelsDao {
  
  @Insert(onConflict = REPLACE)
  suspend fun create(wordsLabelsJoin: WordsLabelsJoin)
  
  @Delete
  suspend fun delete(wordsLabelsJoin: WordsLabelsJoin)
  
  @Query("DELETE FROM words_labels_join WHERE words_labels_join.labelId = :labelId")
  suspend fun deleteFromLabel(labelId: Long)
  
  @Query("DELETE FROM words_labels_join WHERE words_labels_join.wordId = :wordId")
  suspend fun deleteFromWord(wordId: Long)
  
  @Query(
    """
           SELECT * FROM labels
           INNER JOIN words_labels_join
           ON labels.id=words_labels_join.labelId
           WHERE words_labels_join.wordId=:wordId
           """
  )
  fun getLabelsForWord(wordId: Long): LiveData<List<Label>>
  
  @Query(
    """
           SELECT * FROM words
           INNER JOIN words_labels_join
           ON words.id=words_labels_join.wordId
           WHERE words_labels_join.labelId=:labelId
           """
  )
  fun getWordsOfLabel(labelId: Long): LiveData<List<Word>>
}