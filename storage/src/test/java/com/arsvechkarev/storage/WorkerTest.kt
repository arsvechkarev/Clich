package com.arsvechkarev.storage

import com.arsvechkarev.core.model.Word
import com.arsvechkarev.test.DataProvider.wordExhausted
import com.arsvechkarev.test.DataProvider.wordPan
import com.arsvechkarev.test.DataProvider.wordRemarkable
import com.arsvechkarev.test.FakeWordsListStorage
import com.arsvechkarev.test.TestCoroutinesRule
import org.junit.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WorkerTest {
  
  @get:Rule
  val testRule = TestCoroutinesRule()
  
  private var fakeStorage = FakeWordsListStorage()
  
  @Before
  fun setUp() {
    fakeStorage = FakeWordsListStorage()
  }
  
  @Test
  fun `Getting all words list`() = testRule.testCoroutineDispatcher.runBlockingTest{
    val listBefore = mutableListOf(wordExhausted, wordRemarkable)
    fakeStorage.save(listBefore, FILENAME_ALL_WORDS)
  
    val listAfter = Worker.getWords(fakeStorage)
  
    assertEquals(listBefore, listAfter)
  }
  
  @Test
  fun `Deleting word from non-empty list`() = testRule.testCoroutineDispatcher.runBlockingTest {
    val listBefore = mutableListOf(wordExhausted, wordPan)
    fakeStorage.save(listBefore, FILENAME_ALL_WORDS)
    
    Worker.deleteWord(fakeStorage, wordPan)
  
    val listAfter = fakeStorage.get<MutableList<Word>>(FILENAME_ALL_WORDS)
    assertTrue(listAfter!!.size == 1)
    assertTrue(listAfter.contains(wordExhausted))
  }
  
  @Test
  fun `Saving word to non-empty list`() = testRule.testCoroutineDispatcher.runBlockingTest {
    val listBefore = mutableListOf(wordExhausted)
    fakeStorage.save(listBefore, FILENAME_ALL_WORDS)
  
    Worker.saveWord(fakeStorage, wordPan)
  
    val listAfter = fakeStorage.get<MutableList<Word>>(FILENAME_ALL_WORDS)
    assertTrue(listAfter!!.size == 2)
    assertTrue(listAfter.contains(wordPan))
  }
  
  @Test
  fun `Saving word to null list`() = testRule.testCoroutineDispatcher.runBlockingTest {
    val listBefore = null
    fakeStorage.save(listBefore, FILENAME_ALL_WORDS)
  
    Worker.saveWord(fakeStorage, wordPan)
  
    val listAfter = fakeStorage.get<MutableList<Word>>(FILENAME_ALL_WORDS)
    assertTrue(listAfter!!.size == 1)
    assertTrue(listAfter.contains(wordPan))
  }
}