package com.example.finalproject.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.finalproject.R
import com.example.finalproject.database.BookEntity
import com.example.finalproject.database.DatabaseApplication


class AddFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private val viewModel: AddViewModel by viewModels {
        AddViewModelFactory((activity?.application as DatabaseApplication).repository)
    }
    lateinit var book : BookEntity
    private lateinit var allBooks : List<BookEntity>

    // Create instances of EditText as global variables so that other methods can access them
    private lateinit var titleText: EditText
    private lateinit var authorText : EditText
    private lateinit var genre1Text : Spinner
    private lateinit var genre2Text : Spinner

    private var noGenre: String = "None"

    private var genre1String: String = noGenre
    private var genre2String: String = noGenre

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //super.onCreate(savedInstanceState)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)


        titleText = view.findViewById(R.id.title_id)
        authorText = view.findViewById(R.id.author_id)
        genre1Text = view.findViewById(R.id.genre1_id)
        genre2Text = view.findViewById(R.id.genre2_id)
        // set the onItemSelectedListener as (this).  (this) refers to this activity that implements OnItemSelectedListener interface
        genre1Text.onItemSelectedListener = this
        genre2Text.onItemSelectedListener = this


        view.findViewById<Button>(R.id.add_button).setOnClickListener{addButton()}
        view.findViewById<Button>(R.id.update_button).setOnClickListener{updateButton()}
        view.findViewById<Button>(R.id.flood_button).setOnClickListener{floodButton()}

        viewModel.allBooks.observe(viewLifecycleOwner) { books ->
            allBooks = books
        }

        return view
    }

    /**
     * Inserts a new book in the database
     */
    private fun addButton() {
        //accessible as global variables
            //var genre1String
            //var genre2String
        val title = titleText.text.toString()
        var author = authorText.text.toString()

        if (title.isEmpty()){
            showToast("Please enter a title")
            return
        }
        if (author.isEmpty()) {
            author = "Unknown"
        }
        //read which is always false on add, summary is generated upon creation
        viewModel.addNewBook(title, author, genre1String, genre2String)
        clearEditTexts()
    }

    /**
     * Updates book in the database
     */
    private fun updateButton() {
        // Update a record

        //accessible as global variables
            //var genre1String
            //var genre2String
        val title = titleText.text.toString()
        var author = authorText.text.toString()

        if (title.isEmpty()){
            showToast("Please enter a title")
            return
        }
        if (author.isEmpty()) {
            author = "Unknown"
        }
        //every other field is nullable, except read which is always false on add

        viewModel.updateBook(title, author, genre1String, genre2String)
        clearEditTexts()
        showToast("Book Updated")
    }

    /*
    * FOR TESTING PURPOSES
    * adds a lot of books to database, then disables flood button
    * */
    private fun floodButton() {
        view?.findViewById<Button>(R.id.flood_button)?.isEnabled = false
        for (book in books) {
            viewModel.addNewBook(book[0], book[1], noGenre, noGenre)
        }
    }
    private val books = arrayOf(
        arrayOf("Where the Sidewalk Ends", "Shel Silverstein"),
        arrayOf("Hamlet", "William Shakespeare"),
        arrayOf("Romeo and Juliet", "William Shakespeare"),
        arrayOf("Othello", "William Shakespeare"),
        arrayOf("The Shunning", "Beverly Lewis"),
        arrayOf("The Scarlet Letter", "Nathaniel Hawthorne"),
        arrayOf("The Fall of the House of Usher", "Edgar Allan Poe"),
        arrayOf("American Gods", "Neil Gaiman"),
        arrayOf("Equal Rites", "Terry Pratchett")
    )


    private fun showToast(text: String){
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    /**
     * A helper function to clear our editTexts
     */
    private fun clearEditTexts(){
        titleText.text.clear()
        authorText.text.clear()
        genre1String = noGenre
        genre1Text.setSelection(0)
        genre2String = noGenre
        genre2Text.setSelection(0)
    }

    // genre functions
    override fun onNothingSelected(parent: AdapterView<*>?) {
        when (parent!!.id) {
            R.id.genre1_id -> genre1String = noGenre
            R.id.genre2_id -> genre2String = noGenre
        }
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.id) {
            R.id.genre1_id -> genre1String = parent.getItemAtPosition(position).toString()
            R.id.genre2_id -> genre2String = parent.getItemAtPosition(position).toString()
        }
    }

}