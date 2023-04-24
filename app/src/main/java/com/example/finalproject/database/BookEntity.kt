package com.example.finalproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reading_list")
data class BookEntity (
    @PrimaryKey
    var title : String,
    var author : String,    //TODO: set to default to Unknown, How?
    var genre1 : String,
    var genre2 : String,
    var isbn : String?,  //isbn-10
    var read : Boolean,
    var apiCall : String
)