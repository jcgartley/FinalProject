package com.example.finalproject.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.R
import com.example.finalproject.ui.home.HomeViewModel
import com.example.finalproject.database.BookEntity
import com.example.finalproject.database.DatabaseApplication
import com.example.finalproject.ui.add.AddViewModel
import com.example.finalproject.ui.add.AddViewModelFactory

class ViewBookFragment : Fragment() {

    private val viewModel: AddViewModel by viewModels {
        AddViewModelFactory((activity?.application as DatabaseApplication).repository)
    }
    private lateinit var book : BookEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {val listViewModel =
        ViewModelProvider(this)[HomeViewModel::class.java]

        //_binding = FragmentListBinding.inflate(inflater, container, false)
        val context = container?.context
        val root: View = inflater.inflate(R.layout.fragment_viewbookinfo, container, false)

        view?.findViewById<Button>(R.id.deleteButton)?.setOnClickListener{deleteButton()}
        view?.findViewById<Button>(R.id.markAsRead)?.setOnClickListener{markAsRead(root)}

        //TODO: use intent to pass bookEntity


        return root
    }

    //TODO: add are you sure prompt?
    //removes book from Database
    private fun deleteButton() {
        viewModel.deleteBook(book)
    }
    private fun markAsRead(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            if(checked) viewModel.markRead(book)
            else viewModel.markUnread(book)
        }
    }

}