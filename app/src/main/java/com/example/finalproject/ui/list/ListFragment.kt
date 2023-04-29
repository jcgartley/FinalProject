package com.example.finalproject.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.R
import com.example.finalproject.database.DatabaseApplication
import com.example.finalproject.databinding.FragmentListBinding
import com.example.finalproject.ui.add.AddViewModel
import com.example.finalproject.ui.add.AddViewModelFactory
import com.example.finalproject.ui.search.SearchViewModel

class ListFragment : Fragment() {
    //private lateinit var listViewModel: ListViewModel
    private val TAG = "BookList"

    private var _binding: FragmentListBinding? = null

    private val viewModel: AddViewModel by viewModels {
        AddViewModelFactory((activity?.application as DatabaseApplication).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //listViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_list,container,false)
        /*
        val textView: TextView = root.findViewById(R.id.text_list)
        listViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        */
        return root
    }

}