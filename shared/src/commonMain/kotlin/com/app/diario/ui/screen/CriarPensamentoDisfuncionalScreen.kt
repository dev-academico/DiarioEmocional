import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.app.diario.ui.components.Cabecalho

data class CriarPensamentoFormState(
    val situacao: String = "",
    val pensamentosImagens: String = "",
    val emocoesSentimentos: String = "",
    val comportamentosReacoes: String = ""
)

fun formularioPensamentoValido(
    state: CriarPensamentoFormState
): Boolean {
    return state.situacao.isNotBlank() &&
            state.pensamentosImagens.isNotBlank() &&
            state.emocoesSentimentos.isNotBlank() &&
            state.comportamentosReacoes.isNotBlank()
}

fun mensagemErroFormularioPensamento(
    state: CriarPensamentoFormState
): String? {

    if (state.situacao.isBlank()) {
        return "A situação é obrigatória."
    }

    if (state.pensamentosImagens.isBlank()) {
        return "Os pensamentos/imagens são obrigatórios."
    }

    if (state.emocoesSentimentos.isBlank()) {
        return "As emoções/sentimentos são obrigatórios."
    }

    if (state.comportamentosReacoes.isBlank()) {
        return "Os comportamentos/reações são obrigatórios."
    }

    return null
}

@Composable
fun CriarPensamentoDisfuncionalScreen(
    dataRegistro: String,
    onVoltar: () -> Unit,
    onSalvar: (String, String, String, String) -> Unit
) {
    var formState by remember {
        mutableStateOf(CriarPensamentoFormState())
    }

    val valido = formularioPensamentoValido(formState)
    val mensagemErro = mensagemErroFormularioPensamento(formState)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.safeDrawing),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Cabecalho(
            title = "Novo registro de pensamento",
            subtitle = "Preencha seguindo a tabela da psicologia.",
            onVoltar = onVoltar
        )

        OutlinedTextField(
            value = dataRegistro,
            onValueChange = {},
            readOnly = true,
            label = {
                Text("Data")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = formState.situacao,
            onValueChange = { novaSituacao ->
                formState = formState.copy(situacao = novaSituacao)
            },
            label = {
                Text("Situação", modifier = Modifier.semantics{ heading() })
            },
            minLines = 2,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = formState.pensamentosImagens,
            onValueChange = { novoValor ->
                formState = formState.copy(pensamentosImagens = novoValor)
            },
            label = {
                Text("Pensamentos / imagens", modifier = Modifier.semantics{ heading() })
            },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = formState.emocoesSentimentos,
            onValueChange = { novoValor ->
                formState = formState.copy(emocoesSentimentos = novoValor)
            },
            label = {
                Text("Emoções / sentimentos",  modifier = Modifier.semantics{ heading() })
            },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = formState.comportamentosReacoes,
            onValueChange = { novoValor ->
                formState = formState.copy(comportamentosReacoes = novoValor)
            },
            label = {
                Text("Comportamentos / reações",  modifier = Modifier.semantics{ heading() })
            },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        if (mensagemErro != null) {
            Text(
                text = mensagemErro,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = {
                onSalvar(
                    formState.situacao,
                    formState.pensamentosImagens,
                    formState.emocoesSentimentos,
                    formState.comportamentosReacoes
                )
            },
            enabled = valido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar")
        }
    }
}
