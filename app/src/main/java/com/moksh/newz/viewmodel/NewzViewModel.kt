package com.moksh.newz.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moksh.data.Result
import com.moksh.data.model.NewsResponse
import com.moksh.data.repository.NewsRepository
import com.moksh.newz.other.Business
import com.moksh.newz.other.Category
import com.moksh.newz.other.General
import com.moksh.newz.other.Technology
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewzViewModel(private val newzRepository: NewsRepository) :ViewModel(){

    var isDarkTheme by mutableStateOf(false)
        private set

    var categoryList by mutableStateOf(listOf(General(),Business(),Technology()))
        private set

    var activeCategory by mutableStateOf(categoryList[0])
        private set

    var articleUiState by mutableStateOf(ArticleListUiState())
        private set

    init {
        getArticlesByCategory(category = categoryList[0])
    }

    private fun getArticlesByCategory(
        category:Category,
        page: Int = 1
    ){
        viewModelScope.launch {
            val activeState = ArticleListUiState()
            setLoadingState(activeState)

            when(val result = newzRepository.getArticlesByCategory(category.category,page)){
                is Result.Error -> {
                    withContext(Dispatchers.Main){
                        setErrorState(articleUiState,Result.Error(result.errorMessage,result.showRetry))
                    }
                }
                is Result.Success -> {
                    withContext(Dispatchers.Main){
                        setSuccessState(articleUiState,result.data)
                    }
                }
            }
        }
    }

    fun perfomrActions(actions: Actions){
        when(actions){
            is Actions.ChangePageTo -> {
                activeCategory = actions.category
                getArticlesByCategory(activeCategory)
            }
            is Actions.FetchArticles -> {
                getArticlesByCategory(actions.category)
            }
            is Actions.SwitchTheme -> {
                isDarkTheme = !isDarkTheme;
            }
        }
    }

    private fun setLoadingState(articleListUiState: ArticleListUiState){
        articleUiState = articleListUiState.copy(loading = true)
    }

    private fun setErrorState(articleListUiState: ArticleListUiState, error: Result.Error){
        articleUiState = articleListUiState.copy(loading = false, error = error)
    }

    private fun setSuccessState(articleListUiState: ArticleListUiState, data: NewsResponse){
        articleUiState = articleListUiState.copy(loading = false, error = null, articleList = data.articles)
    }

    sealed class Actions(){
        data class ChangePageTo(val category: Category): Actions()
        data class FetchArticles(val category: Category): Actions()
        object SwitchTheme:Actions()
    }
}