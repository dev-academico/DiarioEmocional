package com.app.diario.ui.layout

import CardSistema
import RelatoIndividualComponent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.diario.ui.components.Cabecalho
import com.app.diario.ui.components.FloatingAdd
import com.app.diario.ui.mock.RelatoMock

@Composable
fun RelatosLayoutCelular(
    relatos: List<RelatoMock>,
    modifier: Modifier = Modifier,
    onVoltar: () -> Unit,
    onRelatoClick: (RelatoMock) -> Unit,
    onNovoRelatoClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Cabecalho(
                title = "Relatos",
                subtitle = "Escreva acontecimentos interessantes do seu dia, como foram as coisas no trabalho, etc.",
                onVoltar = onVoltar,
            )
        },
        floatingActionButton = {
            FloatingAdd(
                onClick = onNovoRelatoClick,
                title = "Novo relato",
            )
        },
    ) { padding ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
            contentPadding =
                PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 96.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(relatos) { relato ->
                CardSistema(
                    dataRegistro = relato.dataRegistro,
                    titulo = relato.titulo,
                    onClick = {
                        onRelatoClick(relato)
                    },
                )
            }
        }
    }
}

@Composable
fun RelatosLayoutLargo(
    relatos: List<RelatoMock>,
    relatoSelecionado: RelatoMock? = null,
    onRelatoClick: (RelatoMock) -> Unit = {},
    onNovoRelatoClick: () -> Unit,
    modifier: Modifier = Modifier,
    onVoltar: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Cabecalho(
                title = "Relatos",
                subtitle = "Escreva acontecimentos interessantes do seu dia.",
                onVoltar = onVoltar,
            )
        },
        floatingActionButton = {
            FloatingAdd(
                onClick = onNovoRelatoClick,
                title = "Novo relato",
            )
        },
    ) { padding ->
        Row(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            // LADO ESQUERDO
            LazyColumn(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                contentPadding =
                    PaddingValues(
                        start = 16.dp,
                        end = 8.dp,
                        top = 8.dp,
                        bottom = 96.dp,
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(relatos) { relato ->
                    CardSistema(
                        dataRegistro = relato.dataRegistro,
                        titulo = relato.titulo,
                        onClick = {
                            onRelatoClick(relato)
                        },
                    )
                }
            }

            // LADO DIREITO
            if (relatoSelecionado != null) {
                RelatoIndividualComponent(
                    dataRegistro = relatoSelecionado.dataRegistro,
                    titulo = relatoSelecionado.titulo,
                    conteudo = relatoSelecionado.conteudo,
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                )
            } else {
                Card(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                ) {
                    Text(
                        text = "Selecione um relato",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}
