package com.example.finalproject.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val bookDao: BookDAO) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allBooks: Flow<List<BookEntity>> = bookDao.viewAllBooks()
//    val allUnreadBooks: List<BookEntity> = bookDao.viewUnread()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertBook(book: BookEntity) {
        bookDao.insertBook(book)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteBook(book: BookEntity) {
        bookDao.deleteBook(book)
    }
}