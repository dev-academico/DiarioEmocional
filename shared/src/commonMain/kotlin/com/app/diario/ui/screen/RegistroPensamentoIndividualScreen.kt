import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.diario.ui.components.Cabecalho

@Composable
fun RegistroPensamentoIndividualScreen(
    dataRegistro: String,
    onVoltar: () -> Unit,
) {
    val pensamento =
        pensamentosDisfuncionaisMock.find {
            it.dataRegistro == dataRegistro
        }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(8.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing),
    ) {
        Cabecalho(
            title = "Pensamento disfuncional",
            onVoltar = onVoltar,
        )

        if (pensamento != null) {
            PensamentoIndividualComponent(
                dataRegistro = pensamento.dataRegistro,
                situacao = pensamento.situacao,
                pensamentosImagens = pensamento.pensamentosImagens,
                emocoesSentimentos = pensamento.emocoesSentimentos,
                comportamentosReacoes = pensamento.comportamentosReacoes,
            )
        }
    }
}
