package com.moksh.data.repository

import com.moksh.data.apiservice.NewzApiService
import com.moksh.data.model.NewsResponse
import com.squareup.moshi.Moshi
import com.moksh.data.Result
import com.moksh.data.model.NewsError
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepositoryImpl(
    private val newsApiService: NewzApiService,
    private val moshi: Moshi
) : NewsRepository {
    private val map = HashMap<String, Result.Success<NewsResponse>>()
    override suspend fun getArticlesByCategory(category: String, page: Int): Result<NewsResponse> {
        if (map.containsKey(category)) {
            return map[category] as Result<NewsResponse>
        } else {
            try {
                val response = newsApiService.getArticlesByCateGory(category)
                return if (response.isSuccessful) {
                    if (response.body() != null) {
                        map[category] = Result.Success(response.body()!!)
                        Result.Success(response.body()!!)
                    } else {
                        Result.Error("No Data Found!!")
                    }
                } else {
                    val jsonAdapter: JsonAdapter<NewsError> = moshi.adapter(
                        NewsError::class.java
                    )
                    withContext(Dispatchers.Default) {
                        val newsError = jsonAdapter.fromJson(response.errorBody()?.string()!!)
                        Result.Error(
                            newsError!!.message,
                            showRetry(newsError.code)
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                var errorMessage = e.localizedMessage
                if (e.localizedMessage!!.contains("Unable to resolve host")) {
                    errorMessage = "No internet connection"
                }
                return Result.Error(errorMessage ?: "Something went wrong")
            }
        }
    }

    private fun showRetry(code: String): Boolean = when (code) {
        "apiKeyDisabled", "apiKeyExhausted", "apiKeyInvalid", "apiKeyMissing" -> false
        else -> true
    }
}