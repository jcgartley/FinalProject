package com.example.finalproject.ui.search

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookSearchService {
    //https://api.bookcover.longitood.com/bookcover/?book_title=ThePaleBlueDo&author_name=CarlSaga

    /*
    @GET(".")
    fun getBookCover(@Query("book_title") title: String,
                    @Query("author_name") author: String) : Call<BookData>
    */
    @GET("books/v1/volumes")
    fun getBookCover(@Query("q") q: String) : Call<BookData>

    @GET("/bookcover/{id}")
    fun getBookCoverByISNB(@Path("id") id: Int) : Call <BookData>


}