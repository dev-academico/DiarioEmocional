package com.app.diario.ui.pensamentos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
import com.app.diario.ui.components.PensamentoIndividualComponent

@Composable
fun RegistroPensamentoIndividualScreen(
    viewModel: PensamentoViewModel = viewModel(),
    id: String,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val pensamento =
        when (val tela = estado.tela) {
            is EstadoTela.Sucesso -> tela.pensamentos.find { it.id == id }
            else -> null
        }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(8.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing),
    ) {
        Cabecalho(
            title = "Pensamento disfuncional",
            onVoltar = onVoltar,
        )

        if (pensamento != null) {
            PensamentoIndividualComponent(
                dataRegistro = pensamento.dataRegistro,
                situacao = pensamento.situacao,
                pensamentosImagens = pensamento.pensamentosImagens,
                emocoesSentimentos = pensamento.emocoesSentimentos,
                comportamentosReacoes = pensamento.comportamentosReacoes,
            )
        }
    }
}
