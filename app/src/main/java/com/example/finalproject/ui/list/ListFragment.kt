package com.example.finalproject.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.R
import com.example.finalproject.ui.search.SearchViewModel

class ListFragment : Fragment() {
    private lateinit var listViewModel: ListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listViewModel = ViewModelProvider(this)[ListViewModel::class.java]
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