package com.app.diario.ui.layout

import EstadoVazio
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            EstadoVazio(
                modifier = Modifier,
                titulo = "Nenhum registro de sono ainda",
                inserirNovoComponente = "Toque em “Novo registro” para anotar como foi a sua noite de sono",
            )
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
                EstadoVazio(
                    modifier = Modifier,
                    titulo = "Nenhum registro de sono ainda",
                    inserirNovoComponente = "Toque em “Novo registro” para anotar como foi a sua noite de sono",
                )
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
