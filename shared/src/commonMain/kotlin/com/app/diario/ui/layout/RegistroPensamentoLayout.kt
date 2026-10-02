package com.app.diario.ui.layout

import CardSistema
import PensamentoIndividualComponent
import PensamentoMock
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.diario.ui.components.Cabecalho

@Composable
fun PensamentoLayoutCelular(
    pensamentos: List<PensamentoMock>,
    modifier: Modifier = Modifier,
    onVoltar: () -> Unit,
    onPensamentoClick: (PensamentoMock) -> Unit,
    onNovoPensamentoClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {

        Cabecalho(
            title = "Registro de pensamentos disfuncionais",
            subtitle = "Escreva sobre seus pensamentos disfuncionais seguindo a tabela da psicologia.",
            onVoltar = onVoltar
        )

        Button(
            onClick = onNovoPensamentoClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Novo registro")
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pensamentos) { pensamento ->
                CardSistema(
                    dataRegistro = pensamento.dataHora,
                    titulo = pensamento.situacao,
                    onClick = {
                        onPensamentoClick(pensamento)
                    }
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
    onVoltar: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // LADO ESQUERDO
        Column(
            modifier = Modifier.weight(1f)
        ) {

            Cabecalho(
                title = "Registro de pensamentos disfuncionais",
                subtitle = "Escreva sobre seus pensamentos disfuncionais.",
                onVoltar = onVoltar
            )

            Button(
                onClick = onNovoPensamentoClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Novo registro")
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pensamentos) { pensamento ->
                    CardSistema(
                        dataRegistro = pensamento.dataHora,
                        titulo = pensamento.situacao,
                        onClick = {
                            onPensamentoClick(pensamento)
                        }
                    )
                }
            }
        }

        // LADO DIREITO
        if (pensamentoSelecionado != null) {

            PensamentoIndividualComponent(
                dataHora = pensamentoSelecionado.dataHora,
                situacao = pensamentoSelecionado.situacao,
                pensamentosImagens = pensamentoSelecionado.pensamentosImagens,
                emocoesSentimentos = pensamentoSelecionado.emocoesSentimentos,
                comportamentosReacoes = pensamentoSelecionado.comportamentosReacoes,
                modifier = Modifier.weight(1f)
            )

        } else {

            Card(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Selecione um registro",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}