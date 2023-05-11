package com.example.finalproject.database

import androidx.annotation.WorkerThread

class DatabaseRepository(private val bookDao: BookDAO) {

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
    fun getUnread() = bookDao.viewUnread()
    fun searchGenre(genre: String) = bookDao.findGenre(genre)
    fun byAuthor(author: String) = bookDao.byAuthor(author)
    fun byTitle(title: String) = bookDao.byTitle(title)
    fun getBook(title: String): BookEntity {
        return bookDao.findBook(title)
    }
}