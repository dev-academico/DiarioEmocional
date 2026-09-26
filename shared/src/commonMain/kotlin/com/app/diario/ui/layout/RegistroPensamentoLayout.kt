package com.app.diario.ui.layout

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
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.diario.ui.mock.RelatoMock
import com.app.diario.ui.components.Cabecalho
import androidx.compose.material3.Icon

@Composable
fun PensamentoLayoutCelular(
    pensamentos: List<PensamentoMock>,
    modifier: Modifier = Modifier,
    onVoltar: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {

        Column {
            Cabecalho(
                title = "Registro de pensamentos disfuncionais",
                subtitle = "Escreva sobre seus pensamentos disfuncionais seguindo a tabela da psicologia.",
                onVoltar = onVoltar
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pensamentos) { pensamento ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = pensamento.situacao,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = pensamento.dataHora,
                            style = MaterialTheme.typography.bodySmall
                        )

                    }
                }
            }
        }
    }
}


@Composable
fun PensamentoLayoutLargo(
    pensamentos: List<PensamentoMock>,
    pensamentoSelecionado: PensamentoMock? = null,
    onPensamentoClick: (PensamentoMock) -> Unit = {},
    onVoltar : () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Lista de pensamentos
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pensamentos) { pensamento ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onPensamentoClick(pensamento)
                    }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                    }
                }
            }
        }

        Card(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                if (pensamentoSelecionado != null) {

                    Text(
                        text = pensamentoSelecionado.situacao,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        text = pensamentoSelecionado.dataHora.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )

                    Text(
                        text = pensamentoSelecionado.pensamentosImagens,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = pensamentoSelecionado.emocoesSentimentos,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = pensamentoSelecionado.comportamentosReacoes,
                        style = MaterialTheme.typography.bodyLarge
                    )

                } else {

                    Text(
                        text = "Selecione um pensamento",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}