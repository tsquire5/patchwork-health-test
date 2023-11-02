package com.tsquires.exersise

import com.tsquires.exersise.domain.Book
import com.tsquires.exersise.domain.ISBN
import com.tsquires.exersise.exceptions.AllAvailableBooksAlreadyLentException
import com.tsquires.exersise.exceptions.BookNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class InMemoryLendingLibraryTest {
    @Test
    fun `can find books by author`() {
        val lendingLibrary = getTestLibrary()
        val foundBookss = lendingLibrary.findBooksByAuthor("J K Rowling")
        assertEquals(
            listOf(
                Book(ISBN.fromString("9780747532743"), "Harry Potter And The Philosopher's Stone", "J K Rowling"),
                Book(ISBN.fromString("9780747546290"), "Harry Potter and the Prisoner of Azkaban", "J K Rowling")
            ), foundBookss
        )
    }

    @Test
    fun `unknown author returns empty list`() {
        val lendingLibrary = getTestLibrary()
        val foundBookss = lendingLibrary.findBooksByAuthor("Unknown Author")
        assertEquals(
            emptyList<Book>(), foundBookss
        )
    }

    @Test
    fun `can find books by ISBN`() {
        val lendingLibrary = getTestLibrary()
        val foundBooks = lendingLibrary.findBooksByISBN(ISBN.fromString("9780316206846"))
        assertEquals(
            listOf(Book(ISBN.fromString("9780316206846"), "The Cuckoo's Calling", "Robert Galbraith")), foundBooks
        )
    }

    @Test
    fun `multiple copies of the same book will all be returned when searching by isbn`() {
        val lendingLibrary = getTestLibrary()
        val foundBooks = lendingLibrary.findBooksByISBN(ISBN.fromString("9780552167574"))
        assertEquals(
            listOf(
                Book(ISBN.fromString("9780552167574"), "Feet Of Clay", "Terry Pratchett"),
                Book(ISBN.fromString("9780552167574"), "Feet Of Clay", "Terry Pratchett")
            ), foundBooks
        )
    }

    @Test
    fun `isbn not in our library will return empty list`() {
        val lendingLibrary = getTestLibrary()
        val foundBooks = lendingLibrary.findBooksByISBN(ISBN.fromString("9780060853969"))
        assertEquals(
            emptyList<Book>(),
            foundBooks
        )
    }

    @Test
    fun `can find books by title`() {
        val lendingLibrary = getTestLibrary()
        val foundBooks = lendingLibrary.findBooksByTitle("The Cuckoo's Calling")
        assertEquals(
            listOf(Book(ISBN.fromString("9780316206846"), "The Cuckoo's Calling", "Robert Galbraith")), foundBooks
        )
    }

    @Test
    fun `title not in our library gives empty list`() {
        val lendingLibrary = getTestLibrary()
        val foundBooks = lendingLibrary.findBooksByTitle("Good Omens")
        assertEquals(
            emptyList<Book>(), foundBooks
        )
    }

    @Test
    fun `can borrow a book`() {
        val lendingLibrary = getTestLibrary()
        lendingLibrary.borrowBook(ISBN.fromString("9780316206846"))
    }

    @Test
    fun `cant borrow a book we don't have`() {
        val lendingLibrary = getTestLibrary()
        assertThrows(BookNotFoundException::class.java) { lendingLibrary.borrowBook(ISBN.fromString("9780060853969")) }

    }

    @Test
    fun `can borrow same book twice if there are multiple copies`() {
        val lendingLibrary = getTestLibrary()
        lendingLibrary.borrowBook(ISBN.fromString("9780552167574"))
        lendingLibrary.borrowBook(ISBN.fromString("9780552167574"))
    }


    @Test
    fun `borrowing more than we have throws exception`() {
        val lendingLibrary = getTestLibrary()
        lendingLibrary.borrowBook(ISBN.fromString("9780552167574"))
        lendingLibrary.borrowBook(ISBN.fromString("9780552167574"))
        assertThrows(AllAvailableBooksAlreadyLentException::class.java) { lendingLibrary.borrowBook(ISBN.fromString("9780552167574")) }
    }

    @Test
    fun `cant borrow a reference book`() {
        val lendingLibrary = getTestLibrary()
        assertThrows(AllAvailableBooksAlreadyLentException::class.java) { lendingLibrary.borrowBook(ISBN.fromString("9781784705909")) }
    }

    private fun getTestLibrary(): LendingLibrary {
        return InMemoryLendingLibrary(
            listOf(
                Book(ISBN.fromString("9780747532743"), "Harry Potter And The Philosopher's Stone", "J K Rowling"),
                Book(ISBN.fromString("9780747546290"), "Harry Potter and the Prisoner of Azkaban", "J K Rowling"),
                Book(ISBN.fromString("9780316206846"), "The Cuckoo's Calling", "Robert Galbraith"),
                Book(ISBN.fromString("9780552167574"), "Feet Of Clay", "Terry Pratchett"),
                Book(ISBN.fromString("9780552167574"), "Feet Of Clay", "Terry Pratchett"),
                Book(ISBN.fromString("9781784705909"), "Mr Nice", "Irvine Welsh", isReferenceBook = true)
            )
        )

    }
}