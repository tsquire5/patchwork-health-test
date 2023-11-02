package com.tsquires.exersise.domain

import com.tsquires.exersise.exceptions.InvalidISBNException
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class ISBNTest{
    companion object {
        const val isbnWithInvalidChecksum = "978-3-16-148410-3"
        const val isbnTooLong = "978-3-16-148410-00"
        const val isbnTooShort= "978-3-16-48410-0"
        const val isbnWithInvalidChars= "978-3-16-48.410-0"
        const val validIsbn = "9780747532743"
        const val validIsbnWithHyphens = "978-0-74-753274-3"
        const val validIsbnWithSpaces = "978 0 74 753274 3"

    }
    @ParameterizedTest
    @ValueSource(strings = ["", isbnWithInvalidChecksum, isbnTooLong, isbnTooShort, isbnWithInvalidChars])
    fun `creating an isbn with invalid value fails with correct exception`(isbn: String){
        assertThrows(InvalidISBNException::class.java) { ISBN.fromString(isbn) }
    }

    @ParameterizedTest
    @ValueSource(strings = [validIsbn, validIsbnWithHyphens, validIsbnWithSpaces])
    fun `creating an isbn with value works`(isbn: String){
        assertNotNull( ISBN.fromString(isbn) )
    }
}