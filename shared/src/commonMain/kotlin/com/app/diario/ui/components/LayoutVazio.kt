package com.app.diario.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun LayoutVazio(onVoltar: () -> Unit, entidade: String) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            Cabecalho(
                title = "Relatos",
                subtitle = "Escreva acontecimentos interessantes do seu dia.",
                onVoltar = onVoltar,
            )
        },
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
            Text("Nenhum $entidade cadastrado ainda.")
        }
    }
}