package com.example.finalproject.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val bookDao: BookDAO) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.

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
    suspend fun updateBook(book: BookEntity) {
        bookDao.updateBook(book)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteBook(book: BookEntity) {
        bookDao.deleteBook(book)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun markRead(book: BookEntity) {
        bookDao.markRead(book.title)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun markUnread(book: BookEntity) {
        bookDao.markUnread(book.title)
    }
    fun getAllBooks() = bookDao.viewAllBooks()
    fun getBook(title: String): BookEntity {
        return bookDao.findBook(title)
    }
}