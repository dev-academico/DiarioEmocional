package com.app.diario

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.app.diario.ui.navigation.AppNavigation
import com.app.diario.ui.theme.Theme

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
fun App(initialDeepLink: String? = null) {
    var isDarkMode = isSystemInDarkTheme()

    // O componente Theme aplica automaticamente as cores claras ou escuras
    Theme(darkTheme = isDarkMode) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) {
            AppNavigation()
        }
    }
}
