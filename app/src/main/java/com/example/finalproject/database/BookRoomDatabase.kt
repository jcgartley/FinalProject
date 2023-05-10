package com.example.finalproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [BookEntity::class], version = 4)
abstract class BookRoomDatabase : RoomDatabase(){

    //private val BASE_URL = getString(R.string.summaryAPICall)
    //private val SUMMARY_ERROR = context.getString(R.string.noSum)
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
}
        }
    }
