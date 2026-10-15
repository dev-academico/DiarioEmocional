package com.app.diario.ui.layout

import CardSistema
import PensamentoIndividualComponent
import PensamentoMock
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            items(pensamentos) { pensamento ->
                CardSistema(
                    dataRegistro = pensamento.dataRegistro,
                    titulo = pensamento.situacao,
                    onClick = {
                        onPensamentoClick(pensamento)
                    },
                )
            }
        }
    }
}

@Composable
fun PensamentoLayoutLargo(
    pensamentos: List<PensamentoMock>,
    pensamentoSelecionado: PensamentoMock? = null,
    onPensamentoClick: (PensamentoMock) -> Unit = {},
    onNovoPensamentoClick: () -> Unit,
    modifier: Modifier = Modifier,
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
                onClick = onNovoPensamentoClick, title = "Novo registro"
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
                items(pensamentos) { pensamento ->
                    CardSistema(
                        dataRegistro = pensamento.dataRegistro,
                        titulo = pensamento.situacao,
                        onClick = {
                            onPensamentoClick(pensamento)
                        },
                    )
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
                Card(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                ) {
                    Text(
                        text = "Selecione um registro",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}