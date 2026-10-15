package com.app.diario.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.diario.ui.util.corIntensidade

@Composable
fun IntensidadeBadge(
    intensidade: Int,
    tamanho: Int = 32,
) {
    val cor = corIntensidade(intensidade)
    Box(
        modifier =
            Modifier
                .size(tamanho.dp)
                .clip(CircleShape)
                .background(cor.copy(alpha = 0.15f))
                .semantics(mergeDescendants = true) {},
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = intensidade.toString(),
            color = cor,
            fontWeight = FontWeight.Bold,
            fontSize = (tamanho * 0.42f).sp,
        )
    }
}
