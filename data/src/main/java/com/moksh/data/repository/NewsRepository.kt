package com.moksh.data.repository

import com.moksh.data.model.NewsResponse
import com.moksh.data.Result

interface NewsRepository {
    suspend fun getArticlesByCategory(category: String, page: Int): Result<NewsResponse>
}