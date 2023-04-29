package com.example.finalproject.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.database.BookEntity

class BookListAdapter : ListAdapter<BookEntity, BookListAdapter.BookViewHolder>(BooksComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title, current.author)
    }

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookTitleView: TextView = itemView.findViewById(R.id.titleView)
        private val bookAuthorView: TextView = itemView.findViewById(R.id.authorView)

        fun bind(title: String?, author: String?) {
            bookTitleView.text = title
            bookAuthorView.text = author
        }

        companion object {
            fun create(parent: ViewGroup): BookViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recyclerview_item, parent, false)
                return BookViewHolder(view)
            }
        }
    }

    class BooksComparator : DiffUtil.ItemCallback<BookEntity>() {
        override fun areItemsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
            return (oldItem.title == newItem.title) && (oldItem.author == newItem.author)
        }
    }
}