package com.app.diario.ui.pensamentos

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowSizeClass
import com.app.diario.domain.model.Pensamento
import com.app.diario.ui.components.LayoutCarregando
import com.app.diario.ui.components.LayoutError
import com.app.diario.ui.components.LayoutVazio

@Composable
fun RegistroPensamentoScreen(
    viewModel: PensamentoViewModel = viewModel { PensamentoViewModel() },
    onPensamentoIndividual: (Pensamento) -> Unit,
    onNovoPensamentoClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    var pensamentoSelecionado by remember { mutableStateOf<Pensamento?>(null) }

    when (val tela = estado.tela) {
        EstadoTela.Carregando -> {
            LayoutCarregando("pensamentos")
        }

        EstadoTela.Vazio -> {
            LayoutVazio(
                onVoltar,
                onNovoPensamentoClick,
                "pensamentos",
                "Pensamentos",
                "Escreva sobre seus pensamentos disfuncionais seguindo a tabela da psicologia",
            )
        }

        is EstadoTela.Sucesso -> {
            val pensamentos = tela.pensamentos
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val isCompact =
                !windowSizeClass.isWidthAtLeastBreakpoint(
                    WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND, // 600dp
                )
            if (isCompact) {
                PensamentoLayoutCelular(
                    pensamentos = pensamentos,
                    onPensamentoClick = onPensamentoIndividual,
                    onVoltar = onVoltar,
                    onNovoPensamentoClick = onNovoPensamentoClick,
                )
            } else {
                PensamentoLayoutLargo(
                    pensamentos = pensamentos,
                    pensamentoSelecionado = pensamentoSelecionado,
                    onPensamentoClick = { pensamentoSelecionado = it },
                    onVoltar = onVoltar,
                    onNovoPensamentoClick = onNovoPensamentoClick,
                )
            }
        }

        is EstadoTela.Erro -> {
            LayoutError(onClick = { viewModel.carregar() }, tela.mensagem)
        }
    }
}
