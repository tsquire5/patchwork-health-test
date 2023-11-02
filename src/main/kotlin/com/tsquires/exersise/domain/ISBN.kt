package com.tsquires.exersise.domain

import com.tsquires.exersise.exceptions.InvalidISBNException

data class ISBN private constructor(val stringValue: String){
    companion object{
        fun fromString(stringValue: String): ISBN{
            val parsedString = stringValue.removeHyphens().removeBlanks()
                .apply { this.assertCorrectLength() }
                .apply { this.assertOnlyNumbers() }
                .apply { this.validateChecksum() }
            return ISBN(parsedString)
        }
    }
}

private fun String.assertOnlyNumbers() {
    when{
        this.toLongOrNull() != null -> {}
        else -> throw InvalidISBNException()
    }
}

//Could be ISBN 10 rather than ISBN 13 if its an older book. Ignore that complexity for now as seems out of scope
private fun String.assertCorrectLength() {
   when{
       this.length == 13 -> {}
       else -> throw InvalidISBNException()
   }
}

//https://en.wikipedia.org/wiki/ISBN#ISBN-13_check_digit_calculation
private fun String.validateChecksum() {
    val givenChecksum = this[12].toString().toInt()
    val calculatedChecksum = (10 - (this.substring(0,12).mapIndexed { index, c -> when{
        index % 2 == 0 -> c.toString().toInt()
        else -> c.toString().toInt()* 3
    } }.sum() % 10)) % 10
    return when{
        givenChecksum == calculatedChecksum -> {}
        else -> throw InvalidISBNException()
    }
}

private fun String.removeHyphens(): String {
    return when{
        this.length == 17 -> this.replace("-", "")
        else -> this
    }
}
private fun String.removeBlanks(): String {
    return this.replace(" ", "")
}

