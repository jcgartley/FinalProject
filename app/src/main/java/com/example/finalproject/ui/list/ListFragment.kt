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

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val addViewModel: AddViewModel by viewModels {
        AddViewModelFactory((activity?.application as DatabaseApplication).repository)
    }
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

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = BookListAdapter()
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)


        addViewModel.allBooks.observe(viewLifecycleOwner, Observer {book ->
            // Update the cached copy of the words in the adapter.
            book?.let { adapter.submitList(it) }
        })


        return root
    }

}