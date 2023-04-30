package com.example.finalproject.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.database.BookEntity
import com.example.finalproject.database.DatabaseApplication
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.databinding.FragmentListBinding
import com.example.finalproject.ui.add.AddViewModel
import com.example.finalproject.ui.add.AddViewModelFactory
import com.example.finalproject.ui.home.HomeViewModel
import com.example.finalproject.ui.search.SearchViewModel

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
        //val recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerView.apply {
//            recyclerView?.layoutManager = LinearLayoutManager(activity)
//            recyclerView?.adapter = BookListAdapter.BookViewHolder(itemView)
//        }
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

//package com.example.finalproject.ui.list
//
//import android.content.Context
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.viewModels
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.finalproject.R
//import com.example.finalproject.database.*
//import com.example.finalproject.ui.add.*
//
//abstract class ListFragment : Fragment() {
//
//    private val addViewModel: AddViewModel by viewModels {
//        AddViewModelFactory((activity?.application as DatabaseApplication).repository)
//    }
//    abstract val applicationContext: Context
//    private var layoutManager: RecyclerView.LayoutManager? = null
////                private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
//    private val book = ArrayList<BookEntity>()
//    private lateinit var db: BookRoomDatabase
////                    private var adapter: BookListAdapter.BookViewHolder? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val root = inflater.inflate(R.layout.fragment_list,container,false)
//        //val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
//        return root
//    }
//
//    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(itemView, savedInstanceState)
//        val recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerView.apply {
//            layoutManager = LinearLayoutManager(activity)
//            var adapter = BookListAdapter.BookViewHolder(recyclerView)
//        }
//
//    }
//}