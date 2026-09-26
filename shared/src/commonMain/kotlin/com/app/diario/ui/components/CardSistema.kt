import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CardSistema(
    dataRegistro: String,
    titulo: String,
    onClick: (() -> Unit)? = null
) {
    if (onClick != null) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = dataRegistro,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    } else {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = dataRegistro,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}