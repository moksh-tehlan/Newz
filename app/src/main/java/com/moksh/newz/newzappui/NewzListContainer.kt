package com.moksh.newz.newzappui

import NewzArticleList
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.moksh.newz.ui.theme.articleTitleStyle
import com.moksh.newz.viewmodel.ArticleListUiState
import com.moksh.newz.R

@Composable
fun NewzListContainer(
    uiState: ArticleListUiState,
    retry: () -> Unit,
){
    Surface(
        modifier = Modifier.fillMaxSize().padding(
            start = 10.dp,
            end = 10.dp,
            bottom = 50.dp
        ),
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
    ) {
        when {
            uiState.loading -> {
                CircularLoader()
            }
            uiState.error != null -> {
                ErrorView(
                    errorMessage = uiState.error.errorMessage,
                    showRetry = uiState.error.showRetry,
                    retry = retry
                )
            }
            uiState.articleList?.isEmpty() == false -> {
                NewzArticleList(
                   articles = uiState.articleList,
                )
            }
        }
    }
}
@Composable
private fun CircularLoader() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
fun ErrorView(
    errorMessage: String,
    showRetry: Boolean,
    retry: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            style = articleTitleStyle.copy(color = MaterialTheme.colors.onSurface)
        )
        if (showRetry) {
            TextButton(onClick = retry) {
                Text(
                    text = stringResource(id = R.string.retry),
                    style = TextStyle(
                        color = MaterialTheme.colors.onSurface
                    )
                )
            }
        }
    }
}