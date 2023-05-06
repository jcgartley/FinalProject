package com.example.finalproject.ui.list

import com.example.finalproject.ui.search.BookData
import retrofit2.Call
import retrofit2.http.*

interface BookSummaryService {
//    https://en.wikipedia.org/api/rest_v1/page/summary/Romeo_and_Juliet
    //  /{title}
    @GET("{title}")
    fun getSummary(@Path("title") title: String) : Call<SummaryData>
}

//("title")