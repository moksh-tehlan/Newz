package com.moksh.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsArticle(
    val publishedAt: String,

    val source: NewsSource,

    val title: String,

    val description: String? = null,

    val url: String? = null,

    val urlToImage: String? = null
)
