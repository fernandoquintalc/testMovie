package com.fernando.test.domain

open class Movie constructor(
    open val movieId: Long,
    open val name: String,
    open val genres: Array<Int>,
    open val description: String,
    open val year: String,
    open val language: String,
    open val rate: Double,
    open val poster: String?) : java.io.Serializable