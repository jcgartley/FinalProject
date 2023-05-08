package com.example.finalproject.ui.search

data class BookData (
    //val url: String
    val kind: String,
    val items : List<Item>
)

data class Item(
    val id : String,
    val volumeInfo: Info
)

data class Info(
    val description: String,
    val imageLinks: Picture
)

data class Picture(
    val thumbnail : String
)