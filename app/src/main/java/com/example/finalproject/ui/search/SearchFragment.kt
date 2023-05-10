package com.example.finalproject.ui.search

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.database.DatabaseApplication
import com.example.finalproject.ui.add.AddViewModel
import com.example.finalproject.ui.add.AddViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {
    private val BASE_URL = "https://api.bookcover.longitood.com/bookcover/"
    private var success = true
    private lateinit var title : String
    private lateinit var author : String
    private val viewModel: AddViewModel by viewModels {
        AddViewModelFactory((activity?.application as DatabaseApplication).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookImg  = view.findViewById<ImageView>(R.id.image_view)
        val titleText = view.findViewById<EditText>(R.id.title_name)
        val authorText = view.findViewById<EditText>(R.id.author_name)


        view.findViewById<Button>(R.id.add_button).setOnClickListener{addButton()}

        view.findViewById<Button>(R.id.search_button).setOnClickListener {
            authorText.hideKeyboard()
            title = titleText?.text.toString()//.replace(" ", "+")

            author = authorText?.text.toString()//.replace(" ", "+")
            Log.d(TAG, "onResponse: $title by $author")

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val searchAPI = retrofit.create(BookSearchService::class.java)
            // Using enqueue method allows to make asynchronous call without blocking/freezing main thread
            // randomUserAPI.getUserInfo("us").enqueue  // this end point gets one user only
            // getMultipleUserInfoWithNationality end point gets multiple user info with nationality as parameters

            searchAPI.getBookCover(title,author).enqueue(object :
                Callback<BookData> {

                override fun onFailure(call: Call<BookData>, t: Throwable) {
                    Log.d(TAG, "onFailure : $t")
                }

                override fun onResponse(call: Call<BookData>, response: Response<BookData>) {
                    Log.d(TAG, "onResponse: $response")
                    val body = response.body()

                    if (body != null) {
                        success = true
                        showToast("success")
                        //Toast.makeText(this@MainActivity,"${body.url}",Toast.LENGTH_LONG).show()
                        Glide.with(this@SearchFragment).load(body.url).into(bookImg)
                    }
                    if (body == null){
                        Log.w(TAG, "Valid response was not received")
                        //bookImg.setImageResource(0)
                        success = false
                        showToast("failed")
                        return
                    }
                }
            })

            titleText.text.clear()
            authorText.text.clear()
        }
    }
    // Helper function to hide the keyboard for any view/widget
    private fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun addButton() {
        // Insert a record
            //title, author accessible as global variables
        if (success) {
            val noGenre = "None"
            viewModel.addNewBook(title, author, noGenre, noGenre)
        }
        else showToast("Book not found")
    }
    private fun showToast(text: String){
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

}