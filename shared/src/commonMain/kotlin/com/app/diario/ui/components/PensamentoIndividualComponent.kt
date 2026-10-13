import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun PensamentoIndividualComponent(
    dataRegistro: String,
    situacao: String,
    pensamentosImagens: String,
    emocoesSentimentos: String,
    comportamentosReacoes: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = dataRegistro,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            SecaoPensamento(
                titulo = "Situação",
                conteudo = situacao,
                modifier = Modifier.padding(top = 16.dp)
            )

            SecaoPensamento(
                titulo = "Pensamentos / imagens",
                conteudo = pensamentosImagens,
                modifier = Modifier.padding(top = 16.dp)
            )

            SecaoPensamento(
                titulo = "Emoções / sentimentos",
                conteudo = emocoesSentimentos,
                modifier = Modifier.padding(top = 16.dp)
            )

            SecaoPensamento(
                titulo = "Comportamentos / reações",
                conteudo = comportamentosReacoes,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
private fun SecaoPensamento(
    titulo: String,
    conteudo: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.semantics(mergeDescendants = true){}) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = conteudo,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
