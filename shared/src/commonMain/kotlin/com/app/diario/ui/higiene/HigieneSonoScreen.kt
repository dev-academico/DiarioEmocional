package com.app.diario.ui.higiene

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowSizeClass
import com.app.diario.dados.repositorio.HigieneSonoRepositoryImpl
import com.app.diario.ui.mock.RegistroSonoMock

@Composable
fun HigieneSonoScreen(
    viewModel: HigieneSonoViewModel = viewModel { HigieneSonoViewModel(HigieneSonoRepositoryImpl()) },
    onRegistroIndividual: (RegistroSonoMock) -> Unit,
    onNovoRegistroClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    var registroSelecionado by remember { mutableStateOf<RegistroSonoMock?>(null) }

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact =
        !windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    when (val tela = estado.tela) {
        EstadoSonoTela.Carregando -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Carregando registros de sono...")
                }
            }
        }

        EstadoSonoTela.Vazio -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhum registro de sono encontrado.")
            }
        }

        is EstadoSonoTela.Sucesso -> {
            val registros = tela.registros
            if (isCompact) {
                SonoLayoutCelular(
                    registros = registros,
                    onRegistroClick = onRegistroIndividual,
                    onNovoRegistroClick = onNovoRegistroClick,
                    onVoltar = onVoltar,
                )
            } else {
                SonoLayoutLargo(
                    registros = registros,
                    registroSelecionado = registroSelecionado,
                    onRegistroClick = { registroSelecionado = it },
                    onNovoRegistroClick = onNovoRegistroClick,
                    onVoltar = onVoltar,
                )
            }
        }

        is EstadoSonoTela.Erro -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(tela.mensagem, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.carregarRegistros() }) {
                    Text("Tentar de novo")
                }
            }
        }
    }
}

@Preview
@Composable
fun RegistroSonoScreenPreview() {
    HigieneSonoScreen(
        onRegistroIndividual = {},
        onNovoRegistroClick = {},
        onVoltar = {},
    )
}
