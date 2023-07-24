package com.moksh.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponse(
    var status: String,

    var totalResults: Int,

    var articles : List<NewsArticle>
)
