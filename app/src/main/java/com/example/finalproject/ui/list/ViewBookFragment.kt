package com.example.finalproject.ui.list

import android.content.ContentValues
import android.graphics.Typeface.*
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.R
import com.example.finalproject.database.BookEntity
import com.example.finalproject.database.DatabaseApplication
import com.example.finalproject.ui.add.AddViewModel
import com.example.finalproject.ui.add.AddViewModelFactory
import com.example.finalproject.ui.list.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ViewBookFragment : Fragment() {
    private val BASE_URL = "https://en.wikipedia.org/api/rest_v1/page/summary/"
    private val SUMMARY_ERROR = "Summary cannot be autogenerated at this time"

    private val viewModel: AddViewModel by viewModels {
        AddViewModelFactory((activity?.application as DatabaseApplication).repository)
    }
    private lateinit var book : BookEntity
    private lateinit var read : CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //        val context = container?.context
        val root: View = inflater.inflate(R.layout.fragment_viewbookinfo, container, false)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val summaryAPI = retrofit.create(BookSummaryService::class.java)
        read = root.findViewById(R.id.markAsRead)

        Thread {
            val bundle = this.arguments
            //title guaranteed in list because launched FROM LIST
            val title = bundle!!.getString("title")
            book = viewModel.getBook(title!!)

            //title + author
            val bookTitleView: TextView = root.findViewById(R.id.titleView)
            bookTitleView.text = book.title
            val bookAuthorView: TextView = root.findViewById(R.id.authorView)
            bookAuthorView.text = book.author
            //genres
            val bookGenre1View: TextView = root.findViewById(R.id.genre1View)
            val bookGenre2View: TextView = root.findViewById(R.id.genre2View)
            bookGenre1View.text = book.genre1
            bookGenre2View.text = book.genre2

            //summary call
            val summaryView: TextView = root.findViewById(R.id.summaryView)

             // Using enqueue method allows to make asynchronous call without blocking/freezing main thread
            // randomUserAPI.getUserInfo("us").enqueue  // this end point gets one user only
            // getMultipleUserInfoWithNationality end point gets multiple user info with nationality as parameters
            summaryAPI.getSummary(book.title.replace(" ", "_")).enqueue(object: Callback<SummaryData> {

                override fun onFailure(call: Call<SummaryData>, t: Throwable) {
                    Log.d(ContentValues.TAG, "onFailure : $t")
                }

                override fun onResponse(call: Call<SummaryData>, response: Response<SummaryData>) {
                    Log.d(ContentValues.TAG, "onResponse: $response")

                    val body = response.body()
                    if (body == null) {
                        Log.w(ContentValues.TAG, "Valid response was not received")
                        summaryView.text = SUMMARY_ERROR
                        summaryView.setTypeface(DEFAULT, ITALIC)
                        return
                    }
                    Log.w(ContentValues.TAG, "Valid response was received")

//                    val data = JSONObject(result)
//                    val img = data.getString("image")
                    summaryView.text = body.extract
//                    Glide.with(this@ViewBookFragment).load(body.url).into(summaryView)
                    summaryView.setTypeface(DEFAULT, NORMAL)

                }
            })

            read?.isChecked = book.read
            read?.setOnClickListener{markAsRead()}

            view?.findViewById<Button>(R.id.deleteButton)?.setOnClickListener{deleteButton()}
        }.start()
        return root
    }

    //TODO: add are you sure prompt?
    //TODO: add add button to search
    //TODO: searchable recyclerview

    //removes book from Database
    private fun deleteButton() {
        viewModel.deleteBook(book)
        activity?.onBackPressed()
    }
    //book: BookEntity
    private fun markAsRead() {
        Thread {
            if (read.isChecked) viewModel.markRead(book)
            else viewModel.markUnread(book)
        }.start()
    }

    /*
    * Enables runOnUiThread to work in fragment
    * */
    private fun Fragment?.runOnUiThread(action: () -> Unit) {
        this ?: return
        if (!isAdded) return // Fragment is not attached to an Activity
        activity?.runOnUiThread(action)
    }
    private fun showToast(text: String){
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

}