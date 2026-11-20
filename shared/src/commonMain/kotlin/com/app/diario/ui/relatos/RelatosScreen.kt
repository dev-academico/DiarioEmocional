package com.app.diario.ui.relatos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.app.diario.domain.model.Relato
import com.app.diario.ui.components.Cabecalho
import com.app.diario.ui.components.FloatingAdd
import com.app.diario.ui.mock.RelatoMock

@Composable
fun RelatosScreen(
    viewModel: RelatosViewModel = viewModel { RelatosViewModel() },
    onRelatoIndividual: (Relato) -> Unit,
    onNovoRelatoClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    var relatoSelecionado by remember { mutableStateOf<Relato?>(null) }
    val width = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass

    when (val tela = estado.tela) {
        EstadoTela.Carregando -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Carregando relatos...")
                }
            }
        }

        EstadoTela.Vazio -> {
            Scaffold(
                modifier = Modifier,
                topBar = {
                    Cabecalho(
                        title = "Relatos",
                        subtitle = "Escreva acontecimentos interessantes do seu dia.",
                        onVoltar = onVoltar,
                    )
                },
                floatingActionButton = {
                    FloatingAdd(
                        onClick = onNovoRelatoClick,
                        title = "Novo relato",
                    )
                },
            ) { padding ->
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text("Nenhum relato cadastrado ainda.")
                }
            }

        }

        is EstadoTela.Sucesso -> {
            val relatos = tela.relatos
            when (width) {
                WindowWidthSizeClass.COMPACT -> {
                    RelatosLayoutCelular(
                        relatos = relatos,
                        onRelatoClick = onRelatoIndividual,
                        onVoltar = onVoltar,
                        onNovoRelatoClick = onNovoRelatoClick,
                    )
                }
                else -> {
                    RelatosLayoutLargo(
                        relatos = relatos,
                        relatoSelecionado = relatoSelecionado,
                        onRelatoClick = { relatoSelecionado = it },
                        onVoltar = onVoltar,
                        onNovoRelatoClick = onNovoRelatoClick,
                    )
                }
            }
        }

        is EstadoTela.ErroRespostaInvalida-> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(tela.mensagem, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.carregarRelatos() }) {
                    Text("Tentar de novo")
                }
            }
        }

        is EstadoTela.ErroConexao-> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(tela.mensagem, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.carregarRelatos() }) {
                    Text("Tentar de novo")
                }
            }
        }
    }
}
