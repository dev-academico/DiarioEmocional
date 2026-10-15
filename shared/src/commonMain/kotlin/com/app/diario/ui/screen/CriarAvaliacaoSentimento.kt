package com.app.diario.ui.screen

import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.diario.ui.components.Cabecalho
import com.app.diario.ui.util.corIntensidade
import com.app.diario.ui.util.formatarData
import com.app.diario.ui.util.rotuloIntensidade
import kotlin.math.roundToInt

data class CriarAvaliacaoFormState(
    val intensidade: Int = 5,
    val descricao: String = "",
    val gatilhos: List<String> = emptyList(),
    val novoGatilho: String = "",
)

private val gatilhosSugeridos =
    listOf(
        "Trabalho",
        "Sono ruim",
        "Conflito",
        "Cansaço",
        "Cafeína",
        "Solidão",
        "Prazo apertado",
    )

fun formularioAvaliacaoValido(state: CriarAvaliacaoFormState): Boolean {
    return state.descricao.isNotBlank() &&
            state.gatilhos.isNotEmpty()
}

fun mensagemErroFormularioAvaliacao(state: CriarAvaliacaoFormState): String? {
    if (state.descricao.isBlank()) {
        return "A descrição é obrigatória."
    }

    if (state.gatilhos.isEmpty()) {
        return "Informe pelo menos um gatilho."
    }

    return null
}

private fun CriarAvaliacaoFormState.adicionarGatilho(texto: String): CriarAvaliacaoFormState {
    val limpo = texto.trim()
    val jaExiste = gatilhos.any { it.equals(limpo, ignoreCase = true) }

    return if (limpo.isBlank() || jaExiste) {
        copy(novoGatilho = "")
    } else {
        copy(gatilhos = gatilhos + limpo, novoGatilho = "")
    }
}

private fun CriarAvaliacaoFormState.removerGatilho(texto: String): CriarAvaliacaoFormState {
    return copy(gatilhos = gatilhos.filterNot { it.equals(texto, ignoreCase = true) })
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CriarAvaliacaoScreen(
    dataRegistro: String,
    onVoltar: () -> Unit,
    onSalvar: (Int, String, List<String>) -> Unit,
) {
    var formState by remember {
        mutableStateOf(CriarAvaliacaoFormState())
    }

    val valido = formularioAvaliacaoValido(formState)
    val mensagemErro = mensagemErroFormularioAvaliacao(formState)
    val corAtual = corIntensidade(formState.intensidade)

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(WindowInsets.safeDrawing),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Cabecalho(
            title = "Nova avaliação",
            subtitle = "Registre como você está se sentindo.",
            onVoltar = onVoltar,
        )

        OutlinedTextField(
            value = formatarData(dataRegistro),
            onValueChange = {},
            readOnly = true,
            label = {
                Text("Data", modifier = Modifier.semantics { heading() })
            },
            modifier = Modifier.fillMaxWidth(),
        )

        // Intensidade ---------------------------------------------------------
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Intensidade",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.semantics { heading() },
                )
                Text(
                    text = "${formState.intensidade} · ${rotuloIntensidade(formState.intensidade)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = corAtual,
                )
            }

            Slider(
                value = formState.intensidade.toFloat(),
                onValueChange = { novoValor ->
                    formState = formState.copy(intensidade = novoValor.roundToInt())
                },
                valueRange = 1f..10f,
                steps = 8,
                colors =
                    SliderDefaults.colors(
                        thumbColor = corAtual,
                        activeTrackColor = corAtual,
                    ),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Leve",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "Intensa",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        // Descrição -----------------------------------------------------------
        OutlinedTextField(
            value = formState.descricao,
            onValueChange = { novaDescricao ->
                formState = formState.copy(descricao = novaDescricao)
            },
            label = {
                Text("Descrição", modifier = Modifier.semantics { heading() })
            },
            minLines = 4,
            modifier = Modifier.fillMaxWidth(),
        )

        // Gatilhos ------------------------------------------------------------
        Text(
            text = "Gatilhos",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.semantics { heading() },
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            gatilhosSugeridos.forEach { sugestao ->
                val selecionado = formState.gatilhos.any { it.equals(sugestao, ignoreCase = true) }
                FilterChip(
                    selected = selecionado,
                    onClick = {
                        formState =
                            if (selecionado) {
                                formState.removerGatilho(sugestao)
                            } else {
                                formState.adicionarGatilho(sugestao)
                            }
                    },
                    label = { Text(sugestao) },
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = formState.novoGatilho,
                onValueChange = { texto ->
                    formState = formState.copy(novoGatilho = texto)
                },
                label = { Text("Outro gatilho") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions =
                    KeyboardActions(
                        onDone = {
                            formState = formState.adicionarGatilho(formState.novoGatilho)
                        },
                    ),
                modifier = Modifier.weight(1f),
            )
            OutlinedButton(
                onClick = {
                    formState = formState.adicionarGatilho(formState.novoGatilho)
                },
                enabled = formState.novoGatilho.isNotBlank(),
            ) {
                Text("Adicionar")
            }
        }

        if (formState.gatilhos.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                formState.gatilhos.forEach { gatilho ->
                    InputChip(
                        selected = false,
                        onClick = { formState = formState.removerGatilho(gatilho) },
                        label = { Text(gatilho) },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Remover gatilho $gatilho",
                                modifier = Modifier.size(18.dp),
                            )
                        },
                    )
                }
            }
        }

        if (mensagemErro != null) {
            Text(
                text = mensagemErro,
                color = MaterialTheme.colorScheme.error,
            )
        }

        Button(
            onClick = {
                onSalvar(
                    formState.intensidade,
                    formState.descricao,
                    formState.gatilhos,
                )
            },
            enabled = valido,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Salvar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CriarAvaliacaoScreenPreview() {
    CriarAvaliacaoScreen(
        dataRegistro = "2026-10-15",
        onVoltar = {},
        onSalvar = { _, _, _ -> },
    )
}