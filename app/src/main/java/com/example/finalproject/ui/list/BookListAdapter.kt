package com.example.finalproject.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.database.BookEntity
import com.google.android.material.color.MaterialColors.getColor



class BookListAdapter(private val onClickListener: OnClickListener) : ListAdapter<BookEntity, BookListAdapter.BookViewHolder>(BOOKS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title, current.author, current.read)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(current)
        }
    }

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookTitleView: TextView = itemView.findViewById(R.id.titleView)
        private val bookAuthorView: TextView = itemView.findViewById(R.id.authorView)

        @SuppressLint("ResourceAsColor")
        fun bind(title: String?, author: String?, unread: Boolean) {//
            bookTitleView.text = title
            bookAuthorView.text = author
            if (unread) {
                bookTitleView.setBackgroundColor(R.color.read)
                bookAuthorView.setBackgroundColor(R.color.read)
            }
        }

        companion object {
            fun create(parent: ViewGroup): BookViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recyclerview_item, parent, false)
                return BookViewHolder(view)
            }


        }
    }
    companion object {
        private val BOOKS_COMPARATOR = object : DiffUtil.ItemCallback<BookEntity>() {
            override fun areItemsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
                return oldItem === newItem
            }
            override fun areContentsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
                return (oldItem.title == newItem.title) && (oldItem.author == newItem.author)
            }

        }
        class OnClickListener(val clickListener: (book: BookEntity) -> Unit) {
            fun onClick(book: BookEntity) = clickListener(book)
        }
    }
}