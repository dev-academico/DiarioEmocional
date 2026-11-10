package com.app.diario.ui.relatos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.diario.ui.components.Cabecalho
import com.app.diario.ui.components.RelatoIndividualComponent

@Composable
fun RelatoIndividualScreen(
    viewModel: RelatosViewModel = viewModel(),
    dataRegistro: String,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val relato =
        when (val tela = estado.tela) {
            is EstadoTela.Sucesso -> tela.relatos.find { it.dataRegistro == dataRegistro }
            else -> null
        }

    Column(
        modifier =
            Modifier
                .padding(8.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing),
    ) {
        if (relato != null) {
            Cabecalho(
                title = "Relato Individual",
                onVoltar = onVoltar,
            )
            RelatoIndividualComponent(
                dataRegistro = relato.dataRegistro,
                titulo = relato.titulo,
                conteudo = relato.conteudo,
            )
        }
    }
}
