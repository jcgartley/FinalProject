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
            // if the INSTANCE is null, create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookRoomDatabase::class.java,
                    "reading_list"
                )
                    // Wipes and rebuilds db
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .allowMainThreadQueries()
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
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.bookDAO())
                    }
                }
            }
                suspend fun populateDatabase(bookDao: BookDAO) {
                    // Delete all content
                    bookDao.deleteAll()

                    // Add sample data
                    for (book in books) {
                        bookDao.insertBook(BookEntity(
                            book[0] as String,
                            book[1] as String,
                            book[2] as String,
                            book[3] as String,
                            book[4] as Boolean
                        ))
                    }
                }
    private val books = arrayOf(
        arrayOf("Where the Sidewalk Ends", "Shel Silverstein", "Children", "Poetry", true),
        arrayOf("Catcher in the Rye", "J.D. Salinger", "Classic", "None",true),
        arrayOf("Hamlet", "William Shakespeare", "Classic", "Tragedy", true),
        arrayOf("Romeo and Juliet", "William Shakespeare", "Classic", "Tragedy", true),
        arrayOf("Othello", "William Shakespeare", "Classic", "Tragedy", false),
        arrayOf("The Shunning", "Beverly Lewis", "None", "None", false),
        arrayOf("The Scarlet Letter", "Nathaniel Hawthorne", "Classic", "None", true),
        arrayOf("The Fall of the House of Usher", "Edgar Allan Poe", "Classic", "Horror", false),
        arrayOf("American Gods", "Neil Gaiman", "Fantasy", "None", true),
        arrayOf("Equal Rites", "Terry Pratchett", "Fantasy", "None", false)
    )

            }
        }
    }
