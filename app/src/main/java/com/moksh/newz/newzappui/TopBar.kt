package com.moksh.newz.newzappui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moksh.newz.R
import com.moksh.newz.ui.theme.categoryTitleStyle

@Composable
fun TopBar(@StringRes titleResource:Int, onThemeSwitch: () -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 15.dp, end = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ){
        Text(text = stringResource(id = titleResource),
            style = categoryTitleStyle,
            )
        ThemeSwitcher(onThemeSwitch = {
            onThemeSwitch()
        })
    }
}

@Composable
fun ThemeSwitcher(onThemeSwitch: () -> Unit){
    val isDark = remember { mutableStateOf(false) }
    @DrawableRes val light = R.drawable.ic_light
    @DrawableRes val dark = R.drawable.ic_dark
    IconButton(onClick = {
        onThemeSwitch()
        isDark.value = !isDark.value
    }){
        Icon(
            painter = if(isDark.value) painterResource(dark) else painterResource(light),
            tint = Color.White,
            contentDescription = "theme switcher"
        )
    }
}