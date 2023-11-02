package com.tsquires.exersise.domain

data class Book(val isbn: ISBN, val title: String, val author: String, val isReferenceBook: Boolean = false)