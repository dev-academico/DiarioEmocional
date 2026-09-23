package com.app.diario


import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.app.diario.ui.navigation.AppNavigation
import com.app.diario.ui.theme.Theme
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Suppress("ktlint:standard:function-naming")
@Composable
@Preview
fun App() {
    var isDarkMode by remember { mutableStateOf(false) }

    Theme(
        darkTheme =  isDarkMode
    ) {
        AppNavigation(
            darkTheme = isDarkMode,
            //modifica o tema geral caso uma tela mudar o tema
            onDarkThemeChange = {isDarkMode = it}

        )
    }
}
