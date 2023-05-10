package com.example.finalproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reading_list")
data class BookEntity (
    @PrimaryKey
    var title : String,
    var author : String,
    var genre1 : String,
    var genre2 : String,
    var summary : String,
    var read : Boolean
)


