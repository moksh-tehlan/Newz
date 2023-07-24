package com.moksh.newz.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.moksh.data.apiservice.NewzApiService
import com.moksh.data.repository.NewsRepository
import com.moksh.data.repository.NewsRepositoryImpl
import com.moksh.newz.newzappui.NewzAppUi
import com.moksh.newz.ui.theme.NewzTheme
import com.moksh.newz.viewmodel.NewzViewModel
import com.squareup.moshi.Moshi

class NewzActivity : ComponentActivity() {
    private val viewModel = NewzViewModel(NewsRepositoryImpl(
        newsApiService = NewzApiService.invoke(),
        Moshi.Builder().build()
        ))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewzTheme(isDarkTheme = viewModel.isDarkTheme){
                NewzAppUi(viewModel = viewModel)
            }
        }
    }
}