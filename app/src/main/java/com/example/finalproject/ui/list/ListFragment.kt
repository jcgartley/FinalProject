package com.example.finalproject.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.database.BookEntity
import com.example.finalproject.database.DatabaseApplication
import com.example.finalproject.ui.add.AddViewModel
import com.example.finalproject.ui.add.AddViewModelFactory
import com.example.finalproject.ui.home.HomeViewModel

class ListFragment : Fragment() {
    //private lateinit var listViewModel: ListViewModel
    private val TAG = "BookList"

    //private var _binding: FragmentListBinding? = null
    //private val binding get() = _binding!!

    private val addViewModel: AddViewModel by viewModels {
        AddViewModelFactory((activity?.application as DatabaseApplication).repository)
    }

    lateinit var allBooks : List<BookEntity>

    //private lateinit var recyclerView : RecyclerView
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
        val root: View = inflater.inflate(R.layout.fragment_list, container, false)



        return root
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = BookListAdapter()
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)
        addViewModel.allBooks.observe(viewLifecycleOwner) { books ->
            // Update the cached copy of the words in the adapter.
            books.let { adapter.submitList(it) }
        }
    }
}
