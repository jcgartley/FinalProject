package com.example.finalproject.ui.list

import retrofit2.Call
import retrofit2.http.*

interface BookSummaryService {
//   ex: https://en.wikipedia.org/api/rest_v1/page/summary/Romeo_and_Juliet

    @GET("{title}")
    fun getSummary(@Path("title") title: String) : Call<SummaryData>
}

