package com.moksh.newz.newzappui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moksh.newz.other.Category
import com.moksh.newz.other.getTitleResource
import com.moksh.newz.viewmodel.ArticleListUiState
import com.moksh.newz.viewmodel.NewzViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewzAppUi(viewModel: NewzViewModel){

    val categoryList = viewModel.categoryList
    val activeCategory = viewModel.activeCategory
    val uiState = viewModel.articleUiState

    Scaffold (
        content = {
            BodyContent(
                activeCategory = activeCategory,
                activeCategoryUiState = uiState,
                onThemeSwitch = {
                    viewModel.perfomrActions(NewzViewModel.Actions.SwitchTheme)
                },
                onRetryFetching = {category ->
                    viewModel.perfomrActions(NewzViewModel.Actions.FetchArticles(category))
                }
            )
        },
        bottomBar = {
                BottomBar(
                    categoryList,
                    activeCategory,
                    onMenuClicked = {category ->
                        viewModel.perfomrActions(NewzViewModel.Actions.ChangePageTo(category))
                    }
                )
        }
    )
}

@Composable
fun BodyContent(
    activeCategory: Category,
    activeCategoryUiState: ArticleListUiState,
    onThemeSwitch: ()->Unit,
    onRetryFetching: (Category) -> Unit,
){
    val stringRes = getTitleResource(activeCategory)
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.primary) {
       Column(
           modifier = Modifier.fillMaxSize()
       ) {
           TopBar(stringRes,onThemeSwitch = {
               onThemeSwitch()
           })
           NewzListContainer(uiState = activeCategoryUiState, retry = { onRetryFetching(activeCategory) })
       }
    }
}