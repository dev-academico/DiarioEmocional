package com.app.diario.ui.higiene

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowSizeClass
import com.app.diario.domain.model.HigieneSono
import com.app.diario.ui.components.LayoutCarregando
import com.app.diario.ui.components.LayoutError
import com.app.diario.ui.components.LayoutVazio

@Composable
fun HigieneSonoScreen(
    viewModel: HigieneSonoViewModel = viewModel { HigieneSonoViewModel() },
    onRegistroIndividual: (HigieneSono) -> Unit,
    onNovoRegistroClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    var registroSelecionado by remember { mutableStateOf<HigieneSono?>(null) }

    when (val tela = estado.tela) {
        EstadoSonoTela.Carregando -> {
            LayoutCarregando("registros de sono")
        }

        EstadoSonoTela.Vazio -> {
            LayoutVazio(
                onVoltar = onVoltar,
                onNovoClick = onNovoRegistroClick,
                entidade = "registro de sono",
                title = "Higiene do sono",
                subtitle = "Como foi a sua noite de sono?"
            )
        }

        is EstadoSonoTela.Sucesso -> {
            val registros = tela.registros
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val isCompact =
                !windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

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
            LayoutError(onClick = { viewModel.carregar() }, mensagem = tela.mensagem)
        }
    }
}
