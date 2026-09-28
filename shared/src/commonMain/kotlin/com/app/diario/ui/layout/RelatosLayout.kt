package com.app.diario.ui.layout

import CardSistema
import RelatoIndividualComponent
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
fun RelatosLayoutCelular(
    relatos: List<RelatoMock>,
    modifier: Modifier = Modifier,
    onVoltar: () -> Unit,
    onRelatoClick: (RelatoMock) -> Unit,
    onNovoRelatoClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {

        Cabecalho(
            title = "Relatos",
            subtitle = "Escreva acontecimentos interessantes do seu dia, como foram as coisas no trabalho, etc.",
            onVoltar = onVoltar
        )

        Button(
            onClick = onNovoRelatoClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Novo relato")
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(relatos) { relato ->
                CardSistema(
                    dataRegistro = relato.dataRegistro,
                    titulo = relato.titulo,
                    onClick = {
                        onRelatoClick(relato)
                    }
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
    onVoltar : () -> Unit
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
                title = "Relatos",
                subtitle = "Escreva acontecimentos interessantes do seu dia.",
                onVoltar =  onVoltar
                )

            Button(
                onClick = onNovoRelatoClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Novo relato")
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(relatos) { relato ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onRelatoClick(relato)
                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = relato.titulo,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = relato.dataRegistro,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }

        // LADO DIREITO
        if (relatoSelecionado != null) {

            RelatoIndividualComponent(
                dataRegistro = relatoSelecionado.dataRegistro,
                titulo = relatoSelecionado.titulo,
                conteudo = relatoSelecionado.conteudo,
                modifier = Modifier.weight(1f)
            )

        } else {

            Card(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Selecione um relato",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}