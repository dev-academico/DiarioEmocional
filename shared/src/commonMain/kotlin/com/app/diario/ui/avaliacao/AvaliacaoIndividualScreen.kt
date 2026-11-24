package com.app.diario.ui.avaliacao

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.diario.domain.model.Avaliacao
import com.app.diario.ui.components.AvaliacaoPrevia

@Composable
fun AvaliacaoIndividualScreen(
    viewModel: AvaliacaoViewModel = viewModel { AvaliacaoViewModel() },
    id: String,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val avaliacao =
        when (val tela = estado.tela) {
            is EstadoAvaliacaoTela.Sucesso -> tela.avaliacoes.find { it.id == id }
            else -> null
        }

    AvaliacaoIndividualContent(
        avaliacao = avaliacao,
        onVoltar = onVoltar,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvaliacaoIndividualContent(
    avaliacao: Avaliacao?,
    onVoltar: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalhes da avaliação",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                        )
                    }
                },
            )
        },
    ) { padding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
            contentAlignment = Alignment.TopCenter,
        ) {
            if (avaliacao == null) {
                Text(
                    text = "Avaliação não encontrada",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.Center),
                )
            } else {
                AvaliacaoPrevia(
                    avaliacao = avaliacao,
                    modifier =
                        Modifier
                            .widthIn(max = 720.dp)
                            .fillMaxHeight()
                            .padding(16.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AvaliacaoIndividualScreenPreview() {
    val exemplo =
        Avaliacao(
            id = "1",
            dataRegistro = "2026-10-15",
            intensity = 8,
            descricao = "Dia produtivo, mas com ansiedade antes da reunião.",
            gatilhos = listOf("Trabalho", "Prazo apertado"),
        )
    AvaliacaoIndividualContent(
        avaliacao = exemplo,
        onVoltar = {},
    )
}

@Preview(showBackground = true)
@Composable
fun AvaliacaoIndividualScreenNotFoundPreview() {
    AvaliacaoIndividualContent(
        avaliacao = null,
        onVoltar = {},
    )
}
