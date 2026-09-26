package com.app.diario.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.diario.ui.components.Cabecalho

@Composable
fun HomeScreen(
    onRelatosClick: () -> Unit,
    onPensamentosClick: () -> Unit,
    onPontuacaoClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Diário Emocional",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = "Registre seus dias e melhore sua saúde mental",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Button(
            onClick = onRelatosClick
        ) {
            Text("Relatos")
        }

        Button(
            onClick = onPensamentosClick
        ) {
            Text("Pensamentos disfuncionais")
        }

        Button(
            onClick = onPontuacaoClick
        ) {
            Text("Pontuação diária")
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onRelatosClick = {},
        onPensamentosClick = {},
        onPontuacaoClick = {}
    )
}