package com.example.finalproject.ui.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.finalproject.R
import com.example.finalproject.database.*
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.databinding.FragmentListBinding


class AddFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val TAG = "NewBookForm"

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    //val viewModel by activityViewModels<AddViewModel>()
    private val viewModel: AddViewModel by viewModels {
        AddViewModelFactory((activity?.application as DatabaseApplication).repository)
    }
    lateinit var book : BookEntity
    lateinit var allBooks : List<BookEntity>

    //private lateinit var db: BookRoomDatabase
    // Create instances of EditText as global variables so that other methods can access them
    private lateinit var titleText: EditText
    private lateinit var authorText : EditText
    private lateinit var genre1Text : Spinner
    private lateinit var genre2Text : Spinner
    private lateinit var isbnText : EditText

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
        var view = inflater.inflate(R.layout.fragment_add, container, false)
        //setContentView(R.layout.activity_main)
//        rootView.findViewById(R.id.content)
//            .addView(aboutPage) //<-- Instead of setContentView(aboutPage)

        //val adapter = ReadingListAdapter()

        titleText = view.findViewById(R.id.title_id)
        authorText = view.findViewById(R.id.author_id)
        genre1Text = view.findViewById<Spinner>(R.id.genre1_id)
        genre2Text = view.findViewById<Spinner>(R.id.genre2_id)
        isbnText = view.findViewById(R.id.isbn_id)
        // set the onItemSelectedListener as (this).  (this) refers to this activity that implements OnItemSelectedListener interface
        genre1Text.onItemSelectedListener = this
        genre2Text.onItemSelectedListener = this

        //add, delete, update, view _buttons, TODO: streamline
        view.findViewById<Button>(R.id.add_button).setOnClickListener{addButton(view)}
        //view.findViewById<Button>(R.id.delete_button).setOnClickListener{clearDatabase(view)}
        //view.findViewById<Button>(R.id.update_button).setOnClickListener{updateButton(view)}
        view.findViewById<Button>(R.id.view_button).setOnClickListener{viewAllDataButton(view)}


        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        viewModel.allBooks.observe(viewLifecycleOwner) { books ->
            allBooks = books
        }

        return view
    }

    /**
     * Inserts a new book in the database
     */
    private fun addButton(view: View) {

        // Insert a record

        //accessible as global variables
        //var genre1String
        //var genre2String
        val title = titleText.text.toString()
        var author = authorText.text.toString()
        val isbn = isbnText.text.toString()

        if (title.isEmpty()){
            showToast("Please enter a title")
            return
        }
        if (author.isEmpty()) {
            author = "Unknown"
        }
        //every other field is nullable, except read which is always false on add

        viewModel.addNewBook(title, author, genre1String, genre2String, isbn)
        clearEditTexts()


        runOnUiThread {
            // Do your UI operations
            showToast("Add Button Clicked")
        }
    }

    private fun showToast(text: String){
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    /**
     * A helper function to clear our editTexts
     */
    private fun clearEditTexts(){
        titleText.text.clear()
        authorText.text.clear()
        isbnText.text.clear()
        genre1String = noGenre
        genre2String = noGenre
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

    private fun clearDatabase(view: View) {
//        Thread {
//            var items = viewModel.allBooks
//            for (item in items) {
//                db.bookDAO().deleteBook(item)
//            }
//        }.start()
        //var books = viewModel.allBooks
        for (book in allBooks) {
            viewModel.deleteBook(book)
        }
    }

    /**
     * Updates a book in the database based on the given title
     */
//    private fun updateButton(view: View) {
//
//        val title = titleText.text.toString()
//        if (title.isEmpty()){
//            showToast("A title must be entered!")
//            return
//        }
//
//        Thread {
//            // First find the contact and then update
//            val book  = db.bookDAO().findBook(title.toString())
//            Log.d(TAG, "Updating ${book.title}, ${book.author}")
//
//            // Update the contact
//            book.title = titleText.text.toString()
//            book.author = authorText.text.toString()
//            book.isbn = isbnText.text.toString()
//            book.genre1 = genre1String
//            book.genre2 = genre2String
//
//            db.bookDAO().updateBook(book)
//
//            clearEditTexts()
//
//            // We cannot call showDialog from a non-UI thread, instead we can call it from a runOnUiThread to access our views
//            runOnUiThread {
//                // Do your UI operations
//                showToast("Record Updated Successfully")
//            }
//
//        }.start()
//
//    }

    /**
     * Views all contacts in the database
     */

    private fun viewAllDataButton(view: View) {

        Thread {
            // Read all the records
            //val books = viewModel.allBooks

            val buffer = StringBuffer()
            for (book in allBooks){
                Log.d(TAG, "Book: ${book.title}, ${book.author}")

                buffer.append("Title : ${book.title}" + "\n")
                buffer.append("Author : ${book.author}" + "\n")
                buffer.append("Genres :  ${book.genre1}, ${book.genre2}" + "\n")
                buffer.append("API Call : ${book.apiCall}" + "\n")
                if (book.isbn != "") {buffer.append("ISBN : ${book.isbn}" + "\n\n")}
                else {buffer.append("\n")}
            }


            // We cannot call showDialog from a non-UI thread, instead we can call it from a runOnUiThread to access our views
            runOnUiThread {
                // Do your UI operations
                showDialog("Book", buffer.toString())
            }

        }.start()
    }
    /**
     * show an alert dialog with data dialog.
     */
    private fun showDialog(title: String, Message : String){
        val builder = requireActivity().let { AlertDialog.Builder(it) }
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    /*
    * Enables runOnUiThread to work in fragment
    * */
    private fun Fragment?.runOnUiThread(action: () -> Unit) {
        this ?: return
        if (!isAdded) return // Fragment is not attached to an Activity
        activity?.runOnUiThread(action)
    }

    //pass data from activity to fragment
//    fun passData(database : BookRoomDatabase) {
//        db = database
//    }
}