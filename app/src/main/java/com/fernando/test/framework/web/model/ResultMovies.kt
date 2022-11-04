package com.fernando.test.framework.web.model

import com.google.gson.annotations.SerializedName

data class ResultMovies<T> constructor(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<T>,
    @SerializedName("total_pages") val totalPages : Int
)