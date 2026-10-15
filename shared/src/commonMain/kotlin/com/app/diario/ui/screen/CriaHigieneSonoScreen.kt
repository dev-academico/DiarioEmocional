package com.app.diario.ui.screen

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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.diario.ui.components.Cabecalho
import com.app.diario.ui.mock.DisposicaoAoAcordar
import com.app.diario.ui.mock.QualidadeSono
import com.app.diario.ui.util.formatarData

data class CriarSonoFormState(
    val horarioDormiu: String = "",
    val horarioAcordou: String = "",
    val qualidade: QualidadeSono = QualidadeSono.REGULAR,
    val disposicao: DisposicaoAoAcordar = DisposicaoAoAcordar.MEDIA,
    val usouCelularAntesDeDormir: Boolean = false,
    val tevePesadelos: Boolean = false,
    val comentarioSonhos: String = "",
)

fun formularioSonoValido(state: CriarSonoFormState): Boolean {
    return state.horarioDormiu.isNotBlank() &&
            state.horarioAcordou.isNotBlank()
}

fun mensagemErroFormularioSono(state: CriarSonoFormState): String? {
    if (state.horarioDormiu.isBlank()) {
        return "O horário em que dormiu é obrigatório."
    }

    if (state.horarioAcordou.isBlank()) {
        return "O horário em que acordou é obrigatório."
    }

    return null
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CriarSonoScreen(
    dataRegistro: String,
    onVoltar: () -> Unit,
    onSalvar: (
        horarioDormiu: String,
        horarioAcordou: String,
        qualidade: QualidadeSono,
        disposicao: DisposicaoAoAcordar,
        usouCelularAntesDeDormir: Boolean,
        tevePesadelos: Boolean,
        comentarioSonhos: String,
    ) -> Unit,
) {
    var formState by remember {
        mutableStateOf(CriarSonoFormState())
    }

    val valido = formularioSonoValido(formState)
    val mensagemErro = mensagemErroFormularioSono(formState)

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(WindowInsets.safeDrawing),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Cabecalho(
            title = "Nova higiene do sono",
            subtitle = "Registre como foi a sua noite de sono.",
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

        // Horários ------------------------------------------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedTextField(
                value = formState.horarioDormiu,
                onValueChange = { novoHorario ->
                    formState = formState.copy(horarioDormiu = novoHorario)
                },
                label = {
                    Text("Dormiu às", modifier = Modifier.semantics { heading() })
                },
                placeholder = { Text("23:30") },
                singleLine = true,
                modifier = Modifier.weight(1f),
            )

            OutlinedTextField(
                value = formState.horarioAcordou,
                onValueChange = { novoHorario ->
                    formState = formState.copy(horarioAcordou = novoHorario)
                },
                label = {
                    Text("Acordou às", modifier = Modifier.semantics { heading() })
                },
                placeholder = { Text("06:45") },
                singleLine = true,
                modifier = Modifier.weight(1f),
            )
        }

        // Qualidade do sono ---------------------------------------------------
        Text(
            text = "Qualidade do sono",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.semantics { heading() },
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            QualidadeSono.entries.forEach { opcao ->
                FilterChip(
                    selected = formState.qualidade == opcao,
                    onClick = {
                        formState = formState.copy(qualidade = opcao)
                    },
                    label = { Text(opcao.rotulo) },
                )
            }
        }

        // Disposição ao acordar -----------------------------------------------
        Text(
            text = "Disposição ao acordar",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.semantics { heading() },
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DisposicaoAoAcordar.entries.forEach { opcao ->
                FilterChip(
                    selected = formState.disposicao == opcao,
                    onClick = {
                        formState = formState.copy(disposicao = opcao)
                    },
                    label = { Text(opcao.rotulo) },
                )
            }
        }

        // Hábitos e ocorrências (Booleanos) -----------------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Usou o celular antes de dormir?",
                style = MaterialTheme.typography.bodyLarge,
            )
            Switch(
                checked = formState.usouCelularAntesDeDormir,
                onCheckedChange = { valor ->
                    formState = formState.copy(usouCelularAntesDeDormir = valor)
                },
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Teve pesadelos?",
                style = MaterialTheme.typography.bodyLarge,
            )
            Switch(
                checked = formState.tevePesadelos,
                onCheckedChange = { valor ->
                    formState = formState.copy(tevePesadelos = valor)
                },
            )
        }

        // Comentário sobre sonhos / observações -------------------------------
        OutlinedTextField(
            value = formState.comentarioSonhos,
            onValueChange = { novoComentario ->
                formState = formState.copy(comentarioSonhos = novoComentario)
            },
            label = {
                Text("Comentário sobre sonhos (opcional)", modifier = Modifier.semantics { heading() })
            },
            minLines = 4,
            modifier = Modifier.fillMaxWidth(),
        )

        if (mensagemErro != null) {
            Text(
                text = mensagemErro,
                color = MaterialTheme.colorScheme.error,
            )
        }

        Button(
            onClick = {
                onSalvar(
                    formState.horarioDormiu,
                    formState.horarioAcordou,
                    formState.qualidade,
                    formState.disposicao,
                    formState.usouCelularAntesDeDormir,
                    formState.tevePesadelos,
                    formState.comentarioSonhos,
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
fun CriarSonoScreenPreview() {
    CriarSonoScreen(
        dataRegistro = "2026-10-15",
        onVoltar = {},
        onSalvar = { _, _, _, _, _, _, _ -> },
    )
}