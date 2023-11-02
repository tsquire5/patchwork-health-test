package com.tsquires.exersise

import com.tsquires.exersise.domain.Book
import com.tsquires.exersise.domain.ISBN

interface LendingLibrary {
    fun findBooksByAuthor(author: String): List<Book>
    fun findBooksByTitle(title: String): List<Book>
    fun findBooksByISBN(isbn: ISBN): List<Book>
    fun borrowBook(isbn: ISBN)
    fun lentBookCount(): Int
}