package com.tsquires.exersise

import com.tsquires.exersise.domain.Book
import com.tsquires.exersise.domain.ISBN
import com.tsquires.exersise.exceptions.AllAvailableBooksAlreadyLentException
import com.tsquires.exersise.exceptions.BookNotFoundException

class InMemoryLendingLibrary(private val books: List<Book>) : LendingLibrary {

    private val lentBooks = mutableListOf<Book>()
    override fun findBooksByAuthor(author: String): List<Book> {
        return books.filter { it.author == author }.distinct()
    }

    override fun findBooksByTitle(title: String): List<Book> {
        return books.filter { it.title == title }.distinct()
    }

    override fun findBooksByISBN(isbn: ISBN): List<Book> {
        return books.filter { it.isbn == isbn }
    }

    override fun borrowBook(isbn: ISBN) {
        val inLibraryBooks = books.filter { it.isbn == isbn }
        val nonReferenceBooks = inLibraryBooks.filter { !it.isReferenceBook }
        val alreadyLentBooks = lentBooks.filter { it.isbn == isbn }
        when {
            inLibraryBooks.isEmpty() -> throw BookNotFoundException()
            alreadyLentBooks.size == nonReferenceBooks.size -> throw AllAvailableBooksAlreadyLentException()
            else -> lentBooks.add(nonReferenceBooks.first())
        }
    }

    override fun lentBookCount(): Int = lentBooks.size
}