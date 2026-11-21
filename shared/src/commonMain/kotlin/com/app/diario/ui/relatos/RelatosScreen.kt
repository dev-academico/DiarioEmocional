package com.app.diario.ui.relatos

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowSizeClass
import com.app.diario.domain.model.Relato
import com.app.diario.ui.components.LayoutCarregando
import com.app.diario.ui.components.LayoutError
import com.app.diario.ui.components.LayoutVazio

@Composable
fun RelatosScreen(
    viewModel: RelatosViewModel = viewModel { RelatosViewModel() },
    onRelatoIndividual: (Relato) -> Unit,
    onNovoRelatoClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    var relatoSelecionado by remember { mutableStateOf<Relato?>(null) }

    when (val tela = estado.tela) {
        EstadoTela.Carregando -> {
            LayoutCarregando("relatos")
        }

        is EstadoTela.Sucesso -> {
            val relatos = tela.relatos
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val isCompact = !windowSizeClass.isWidthAtLeastBreakpoint(
                WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND // 600dp
            )

            if (isCompact) {
                RelatosLayoutCelular(
                    relatos = relatos,
                    onRelatoClick = onRelatoIndividual,
                    onVoltar = onVoltar,
                    onNovoRelatoClick = onNovoRelatoClick
                )
            } else {
                RelatosLayoutLargo(
                    relatos = relatos,
                    relatoSelecionado = relatoSelecionado,
                    onRelatoClick = { relatoSelecionado = it },
                    onVoltar = onVoltar,
                    onNovoRelatoClick = onNovoRelatoClick
                )
            }
        }

        EstadoTela.Vazio -> {
            LayoutVazio(onVoltar, onNovoRelatoClick, "relato", "Relatos", "Escreva acontecimentos interessantes do seu dia.")
        }

        is EstadoTela.Erro-> {
            LayoutError(onClick = { viewModel.carregar() }, tela.mensagem)
        }
    }
}
