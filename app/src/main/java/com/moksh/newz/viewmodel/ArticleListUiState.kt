package com.moksh.newz.viewmodel

import com.moksh.data.Result
import com.moksh.data.model.NewsArticle

data class ArticleListUiState (
    val loading: Boolean = true,
    val articleList: List<NewsArticle>? = emptyList(),
    val error: Result.Error? = null
)