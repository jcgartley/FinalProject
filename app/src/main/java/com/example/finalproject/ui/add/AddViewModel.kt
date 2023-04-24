package com.example.finalproject.ui.add

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.Factory
import kotlinx.coroutines.launch
import com.example.finalproject.database.*

class AddViewModel (private val repository: DatabaseRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allBooks: LiveData<List<BookEntity>> = repository.allBooks.asLiveData()
    val allUnreadBooks: List<BookEntity> = repository.allUnreadBooks

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

class AddViewModelFactory(private val repository: DatabaseRepository) : Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
