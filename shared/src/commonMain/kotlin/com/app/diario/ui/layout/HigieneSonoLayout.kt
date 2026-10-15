package com.app.diario.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.diario.ui.components.Cabecalho
import com.app.diario.ui.components.FloatingAdd
import com.app.diario.ui.components.SonoCard
import com.app.diario.ui.components.SonoPrevia
import com.app.diario.ui.mock.RegistroSonoMock

@Composable
fun SonoLayoutCelular(
    registros: List<RegistroSonoMock>,
    onRegistroClick: (RegistroSonoMock) -> Unit,
    onNovoRegistroClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    Scaffold(
        topBar = {
            Cabecalho(
                title = "Registro de sono",
                subtitle = "Como foi a sua noite de sono?",
                onVoltar = onVoltar,
            )
        },
        floatingActionButton = { FloatingAdd(onClick = onNovoRegistroClick, "Novo registro") },
    ) { padding ->
        if (registros.isEmpty()) {
            EstadoVazioSono(modifier = Modifier.padding(padding))
        } else {
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
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(registros) { registro ->
                    SonoCard(
                        registro = registro,
                        selecionado = false,
                        onClick = { onRegistroClick(registro) },
                    )
                }
            }
        }
    }
}

// Layout largo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SonoLayoutLargo(
    registros: List<RegistroSonoMock>,
    registroSelecionado: RegistroSonoMock?,
    onRegistroClick: (RegistroSonoMock) -> Unit,
    onNovoRegistroClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    Scaffold(
        topBar = {
            Cabecalho(
                title = "Registro de sono",
                subtitle = "Como foi a sua noite de sono?",
                onVoltar = onVoltar,
            )
        },
        floatingActionButton = { FloatingAdd(onClick = onNovoRegistroClick, "Novo registro") },
    ) { padding ->
        Row(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            if (registros.isEmpty()) {
                EstadoVazioSono(modifier = Modifier.weight(1f))
            } else {
                LazyColumn(
                    modifier =
                        Modifier
                            .weight(0.45f)
                            .fillMaxHeight(),
                    contentPadding =
                        PaddingValues(
                            start = 16.dp,
                            end = 8.dp,
                            top = 8.dp,
                            bottom = 96.dp,
                        ),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(registros) { registro ->
                        SonoCard(
                            registro = registro,
                            selecionado = registro == registroSelecionado,
                            onClick = { onRegistroClick(registro) },
                        )
                    }
                }

                SonoPrevia(
                    registro = registroSelecionado,
                    modifier =
                        Modifier
                            .weight(0.55f)
                            .fillMaxHeight()
                            .padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                )
            }
        }
    }
}

// Vazio

@Composable
private fun EstadoVazioSono(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Nenhum registro de sono ainda",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = "Toque em “Novo registro” para anotar como foi a sua noite de sono.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 32.dp),
            )
        }
    }
}
