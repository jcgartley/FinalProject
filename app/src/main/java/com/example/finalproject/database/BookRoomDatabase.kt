package com.example.finalproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [BookEntity::class], version = 4)
abstract class BookRoomDatabase : RoomDatabase(){
    abstract fun bookDAO() : BookDAO

    companion object {
        @Volatile
        private var INSTANCE: BookRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BookRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookRoomDatabase::class.java,
                    "reading_list"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this code lab.
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope)).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
//
        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.bookDAO())
                    }
                }
            }
                suspend fun populateDatabase(bookDao: BookDAO) {
                    // Delete all content here.
                    bookDao.deleteAll()

                    // Add sample words.
                    var book = BookEntity("Catcher in the Rye", "J.D. Salinger", "Classic", "",true)
                    bookDao.insertBook(book)
                    book = BookEntity("Romeo and Juliet", "William Shakespeare", "Classic", "Tragedy", false)
                    bookDao.insertBook(book)
                    book = BookEntity("Where the Sidewalk Ends", "Shel Silverstein", "Children", "Poetry", false)
                    bookDao.insertBook(book)
                }

            }
        }
    }
