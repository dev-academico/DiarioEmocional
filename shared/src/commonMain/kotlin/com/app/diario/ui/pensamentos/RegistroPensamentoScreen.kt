package com.app.diario.ui.pensamentos

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowWidthSizeClass
import com.app.diario.ui.mock.*

@Composable
fun RegistroPensamentoScreen(
    viewModel: PensamentoViewModel = viewModel { PensamentoViewModel() },
    onPensamentoIndividual: (PensamentoMock) -> Unit,
    onNovoPensamentoClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    var pensamentoSelecionado by remember { mutableStateOf<PensamentoMock?>(null) }
    val width = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass

    when (val tela = estado.tela) {
        EstadoPensamentoTela.Carregando -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Carregando pensamentos...")
                }
            }
        }

        EstadoPensamentoTela.Vazio -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhum pensamento registrado ainda.")
            }
        }

        is EstadoPensamentoTela.Sucesso -> {
            val pensamentos = tela.pensamentos
            when (width) {
                WindowWidthSizeClass.COMPACT -> {
                    PensamentoLayoutCelular(
                        pensamentos = pensamentos,
                        onPensamentoClick = onPensamentoIndividual,
                        onVoltar = onVoltar,
                        onNovoPensamentoClick = onNovoPensamentoClick,
                    )
                }
                else -> {
                    PensamentoLayoutLargo(
                        pensamentos = pensamentos,
                        pensamentoSelecionado = pensamentoSelecionado,
                        onPensamentoClick = { pensamentoSelecionado = it },
                        onVoltar = onVoltar,
                        onNovoPensamentoClick = onNovoPensamentoClick,
                    )
                }
            }
        }

        is EstadoPensamentoTela.Erro -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(tela.mensagem, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.carregarPensamentos() }) {
                    Text("Tentar de novo")
                }
            }
        }
    }
}
