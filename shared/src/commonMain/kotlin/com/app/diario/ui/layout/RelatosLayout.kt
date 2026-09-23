package com.app.diario.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun RelatosLayoutCelular(
    relatos: List<RelatoMock>,
    modifier: Modifier = Modifier,
    onVoltar: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {

        Cabecalho(
            title = "Relatos",
            subtitle = "Escreva acontecimentos interessantes do seu dia, como foram as coisas no trabalho, etc."
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(relatos) { relato ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = relato.titulo,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = relato.dataRegistro.toString(),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            text = relato.conteudo,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
        IconButton(
            onClick =  onVoltar
        ){
            Text(
                text = "←",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}


@Composable
fun RelatosLayoutLargo(
    relatos: List<RelatoMock>,
    relatoSelecionado: RelatoMock? = null,
    onRelatoClick: (RelatoMock) -> Unit = {},
    onVoltar : () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Lista de relatos
        LazyColumn(
            modifier = Modifier.weight(1f),
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
                            text = relato.dataRegistro.toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        // Detalhes do relato selecionado
        Card(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                if (relatoSelecionado != null) {

                    Text(
                        text = relatoSelecionado.titulo,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        text = relatoSelecionado.dataRegistro.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )

                    Text(
                        text = relatoSelecionado.conteudo,
                        style = MaterialTheme.typography.bodyLarge
                    )

                } else {

                    Text(
                        text = "Selecione um relato",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            IconButton(
                onClick =  onVoltar
            ){
                Text(
                    text = "←",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}