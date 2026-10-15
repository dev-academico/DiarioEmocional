package com.app.diario.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.diario.ui.mock.RegistroSonoMock
import com.app.diario.ui.mock.registrosSonoMock
import com.app.diario.ui.util.calcularDuracaoFormatada
import com.app.diario.ui.util.formatarData

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SonoCard(
    registro: RegistroSonoMock,
    selecionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val containerColor =
        if (selecionado) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceContainerLow
        }

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(
                        text = formatarData(registro.dataRegistro),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Text(
                        text = registro.disposicao.rotulo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                    )
                }

                Spacer(Modifier.size(8.dp))
                val duracao = calcularDuracaoFormatada(registro.horarioDormiu, registro.horarioAcordou)
                Text(
                    text = duracao,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium,
                )
            }

            // Linha 3: Tags rápidas (Disposição, Celular, Pesadelos)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                if (registro.usouCelularAntesDeDormir) {
                    TagInfo(texto = "Celular")
                }
                if (registro.tevePesadelos) {
                    TagInfo(texto = "Pesadelo")
                }
            }

            // Linha 4: Prévia do comentário (se houver)
            if (registro.comentarioSonhos.isNotBlank()) {
                Text(
                    text = registro.comentarioSonhos,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun TagInfo(texto: String) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}

// ==========================================
// PREVIEWS
// ==========================================

@Preview(showBackground = true)
@Composable
private fun SonoCardPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            SonoCard(
                registro = registrosSonoMock[0],
                selecionado = false,
                onClick = {},
            )
            SonoCard(
                registro = registrosSonoMock[2],
                selecionado = true,
                onClick = {},
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 450, heightDp = 500)
@Composable
private fun SonoPreviaPreview() {
    MaterialTheme {
        SonoPrevia(
            registro = registrosSonoMock.first(),
            modifier = Modifier.padding(16.dp),
        )
    }
}
