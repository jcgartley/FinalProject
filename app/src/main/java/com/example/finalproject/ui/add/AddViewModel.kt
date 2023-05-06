package com.example.finalproject.ui.add

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.example.finalproject.database.DatabaseRepository
import com.example.finalproject.database.BookEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AddViewModel (private val repository: DatabaseRepository) : ViewModel() {


    val allBooks: LiveData<List<BookEntity>> = repository.getAllBooks().asLiveData()

    fun getBook(title: String): BookEntity {
        return repository.getBook(title)
    }
    private fun insertBook(book: BookEntity) = viewModelScope.launch {
        repository.insertBook(book)
    }
    private fun updateBook(book: BookEntity) = viewModelScope.launch {
        repository.updateBook(book)
    }
    fun markRead(book: BookEntity) = viewModelScope.launch {
        repository.markRead(book)
    }
    fun markUnread(book: BookEntity) = viewModelScope.launch {
        repository.markUnread(book)
    }

    private fun getNewBook(title : String,
                           author : String,
                           genre1 : String,
                           genre2 : String,
                           isbn : String?): BookEntity {
        return BookEntity(
            title = title,
            author = author,
            genre1 = genre1,
            genre2 = genre2,
            isbn = isbn,
            read = false
        )
    }
    fun addNewBook(title : String,
                   author : String,
                   genre1 : String,
                   genre2 : String,
                   isbn : String?) {
        val newBook = getNewBook(title, author, genre1, genre2, isbn)
        insertBook(newBook)
    }
    fun updateBook(title : String,
                   author : String,
                   genre1 : String,
                   genre2 : String,
                   isbn : String?) {
        val newBook = getNewBook(title, author, genre1, genre2, isbn)
        updateBook(newBook)
    }
    fun deleteBook(book: BookEntity) = viewModelScope.launch {
        repository.deleteBook(book)
    }


}

class AddViewModelFactory(private val repository: DatabaseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
