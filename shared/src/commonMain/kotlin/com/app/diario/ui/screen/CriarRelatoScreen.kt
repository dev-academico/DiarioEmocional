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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.diario.ui.components.Cabecalho
import java.time.LocalDate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class CriarRelatoFormState(
    val titulo: String = "",
    val conteudo: String = ""
)

fun formularioRelatoValido(
    state: CriarRelatoFormState
): Boolean {
    return state.titulo.isNotBlank() &&
            state.conteudo.isNotBlank()
}

fun mensagemErroFormulario(
    state: CriarRelatoFormState
): String? {

    if (state.titulo.isBlank()) {
        return "O título é obrigatório."
    }

    if (state.conteudo.isBlank()) {
        return "O conteúdo é obrigatório."
    }

    return null
}
@Composable
fun CriarRelatoScreen(
    dataRegistro: String,
    onVoltar: () -> Unit,
    onSalvar: (String, String) -> Unit
) {
    var formState by remember {
        mutableStateOf(CriarRelatoFormState())
    }

    val valido = formularioRelatoValido(formState)
    val mensagemErro = mensagemErroFormulario(formState)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.safeDrawing),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Cabecalho(
            title = "Novo relato",
            subtitle = "Registre os acontecimentos do seu dia.",
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
            value = formState.titulo,
            onValueChange = { novoTitulo ->
                formState = formState.copy(titulo = novoTitulo)
            },
            label = {
                Text("Título")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = formState.conteudo,
            onValueChange = { novoConteudo ->
                formState = formState.copy(conteudo = novoConteudo)
            },
            label = {
                Text("Conteúdo")
            },
            minLines = 6,
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
                onSalvar(formState.titulo, formState.conteudo)
            },
            enabled = valido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar")
        }
    }
}