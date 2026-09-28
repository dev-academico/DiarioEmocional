import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.diario.ui.components.Cabecalho
import com.app.diario.ui.mock.relatosMock

@Composable
fun RelatoIndividualScreen(
    dataRegistro: String,
    onVoltar: () -> Unit
) {
    val relato = relatosMock.find {
        it.dataRegistro == dataRegistro
    }

    Column(
        modifier = Modifier
            .padding(32.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {

        if (relato != null) {
            Cabecalho(
                title = "Relato Individual",
                onVoltar = onVoltar

            )
            RelatoIndividualComponent(
                dataRegistro = relato.dataRegistro,
                titulo = relato.titulo,
                conteudo = relato.conteudo
            )
        }
    }
}