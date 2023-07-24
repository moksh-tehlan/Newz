package com.moksh.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsError(
    val status: String,

    val code: String,

    val message: String
)
