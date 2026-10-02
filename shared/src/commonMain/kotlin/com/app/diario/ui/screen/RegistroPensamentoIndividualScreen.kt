import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegistroPensamentoIndividualScreen(
    dataRegistro: String,
    onVoltar: () -> Unit
) {
    val pensamento = pensamentosDisfuncionaisMock.find {
        it.dataHora == dataRegistro
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(onClick = onVoltar) {
            Text(
                text = "←",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        if (pensamento != null) {
            PensamentoIndividualComponent(
                dataHora = pensamento.dataHora,
                situacao = pensamento.situacao,
                pensamentosImagens = pensamento.pensamentosImagens,
                emocoesSentimentos = pensamento.emocoesSentimentos,
                comportamentosReacoes = pensamento.comportamentosReacoes
            )
        }
    }
}