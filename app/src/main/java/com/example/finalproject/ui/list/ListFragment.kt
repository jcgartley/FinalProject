package com.example.finalproject.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.database.BookEntity
import com.example.finalproject.database.DatabaseApplication
import com.example.finalproject.ui.add.AddViewModel
import com.example.finalproject.ui.add.AddViewModelFactory


class ListFragment : Fragment() {
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
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = BookListAdapter(BookListAdapter.Companion.OnClickListener {
            val nextFrag = ViewBookFragment()
            val bundle = Bundle()
            bundle.putString("title", it.title)
            nextFrag.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_list, nextFrag)
                .addToBackStack(null)
                .commit()
        })
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)


        addViewModel.allBooks.observe(viewLifecycleOwner) { books ->
            // Update the cached copy of the words in the adapter.
            books.let { adapter.submitList(it) }
        }
    }
}
