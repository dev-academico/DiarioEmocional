package com.app.diario.ui.layout

import CardSistema
import EstadoVazio
import PensamentoIndividualComponent
import PensamentoMock
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.diario.ui.components.Cabecalho
import com.app.diario.ui.components.FloatingAdd

@Composable
fun PensamentoLayoutCelular(
    pensamentos: List<PensamentoMock>,
    modifier: Modifier = Modifier,
    onVoltar: () -> Unit,
    onPensamentoClick: (PensamentoMock) -> Unit,
    onNovoPensamentoClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Cabecalho(
                title = "Registro de pensamentos disfuncionais",
                subtitle = "Escreva sobre seus pensamentos disfuncionais seguindo a tabela da psicologia.",
                onVoltar = onVoltar,
            )
        },
        floatingActionButton = {
            FloatingAdd(onClick = onNovoPensamentoClick, title = "Novo pensamento")
        },
    ) { padding ->
        if (pensamentos.isEmpty()) {
            EstadoVazio(
                modifier = Modifier,
                titulo = "Nenhum registros de pensamentos disfuncionais ainda.",
                inserirNovoComponente = "Toque em “Novo registro” para registrar um pensamento disfuncional",
            )
        } else {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                contentPadding =
                    PaddingValues(
                        start = 8.dp,
                        end = 8.dp,
                        top = 8.dp,
                        bottom = 96.dp,
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(pensamentos) { pensamento ->
                    CardSistema(
                        dataRegistro = pensamento.dataRegistro,
                        titulo = pensamento.situacao,
                        onClick = { onPensamentoClick(pensamento) },
                    )
                }
            }
        }
    }
}

@Composable
fun PensamentoLayoutLargo(
    modifier: Modifier = Modifier,
    pensamentos: List<PensamentoMock>,
    pensamentoSelecionado: PensamentoMock? = null,
    onPensamentoClick: (PensamentoMock) -> Unit = {},
    onNovoPensamentoClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Cabecalho(
                title = "Registro de pensamentos disfuncionais",
                subtitle = "Escreva sobre seus pensamentos disfuncionais.",
                onVoltar = onVoltar,
            )
        },
        floatingActionButton = {
            FloatingAdd(
                onClick = onNovoPensamentoClick,
                title = "Novo registro",
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
            if (pensamentos.isEmpty()) {
                EstadoVazio(
                    modifier = Modifier.weight(1f),
                    titulo = "Nenhum registros de pensamentos disfuncionais ainda.",
                    inserirNovoComponente = "Toque em “Novo registro” para registrar um pensamento disfuncional",
                )
            } else {
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
                    items(pensamentos) { pensamento ->
                        CardSistema(
                            dataRegistro = pensamento.dataRegistro,
                            titulo = pensamento.situacao,
                            onClick = { onPensamentoClick(pensamento) },
                        )
                    }
                }
            }

            // LADO DIREITO
            if (pensamentoSelecionado != null) {
                PensamentoIndividualComponent(
                    dataRegistro = pensamentoSelecionado.dataRegistro,
                    situacao = pensamentoSelecionado.situacao,
                    pensamentosImagens = pensamentoSelecionado.pensamentosImagens,
                    emocoesSentimentos = pensamentoSelecionado.emocoesSentimentos,
                    comportamentosReacoes = pensamentoSelecionado.comportamentosReacoes,
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                )
            } else {
                Surface(
                    modifier =
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surfaceContainer,
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text(
                            text = if (pensamentos.isEmpty()) "Crie seu primeiro registro" else "Selecione um registro",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}
