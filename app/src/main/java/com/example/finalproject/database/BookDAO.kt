package com.example.finalproject.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDAO {
    //TODO: Prompt user that book exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Update
    suspend fun updateBook(book: BookEntity)

    @Query("UPDATE reading_list SET read = 1 WHERE title = :idtitle")
    fun markRead(idtitle: String)
    @Query("UPDATE reading_list SET read = 0 WHERE title = :idtitle")
    fun markUnread(idtitle: String)

    @Delete
    suspend fun deleteBook(book: BookEntity)

    @Query("DELETE FROM reading_list")
    suspend fun deleteAll()

    @Query("SELECT * FROM reading_list")
    fun viewAllBooks() : Flow<List<BookEntity>>

    @Query("SELECT * FROM reading_list WHERE read = 0") //0 = false
    fun viewUnread() : Flow<List<BookEntity>>

    @Query("SELECT * FROM reading_list WHERE title = :title")
    fun findBook(title: String) : BookEntity

}