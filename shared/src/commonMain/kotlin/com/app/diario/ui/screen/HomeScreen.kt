package com.app.diario.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
            .padding(16.dp)
    ) {

        Cabecalho(
            title = "Diário Emocional",
            subtitle = "Registre seus dias e melhore sua saúde mental"
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