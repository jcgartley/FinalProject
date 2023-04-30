package com.example.finalproject.ui.add

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.example.finalproject.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AddViewModel (private val repository: DatabaseRepository) : ViewModel() {

      //private lateinit var repository : DatabaseRepository

    val allBooks: LiveData<List<BookEntity>> = repository.getAllBooks().asLiveData()

    private fun insertBook(book: BookEntity) = viewModelScope.launch {
        repository.insertBook(book)
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
            read = false,
            apiCall = getAPICall(title)
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
    fun deleteBook(book: BookEntity) = viewModelScope.launch {
        repository.deleteBook(book)
    }

    private fun getAPICall(title: String): String {
        var underscoreTitle = title.replace(" ", "_")
        return "https://en.wikipedia.org/api/rest_v1/page/summary/$underscoreTitle"
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
