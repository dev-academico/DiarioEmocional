package com.app.diario.ui.components

import AvaliacaoMock
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.app.diario.ui.util.corIntensidade
import com.app.diario.ui.util.formatarData
import com.app.diario.ui.util.rotuloIntensidade

@Composable
fun AvaliacaoCard(
    avaliacao: AvaliacaoMock,
    selecionada: Boolean,
    onClick: () -> Unit,
) {
    val intensidade = avaliacao.intensity.toInt()

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().semantics(mergeDescendants = true) {},
        shape = RoundedCornerShape(20.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    if (selecionada) {
                        MaterialTheme.colorScheme.secondaryContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceContainerLow
                    },
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (selecionada) 4.dp else 1.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    IntensidadeBadge(intensidade = intensidade)
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            text = formatarData(avaliacao.dataRegistro),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = rotuloIntensidade(intensidade),
                            style = MaterialTheme.typography.labelMedium,
                            color = corIntensidade(intensidade),
                        )
                    }
                }

                Spacer(Modifier.size(8.dp))

                Text(
                    text = avaliacao.descricao,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
