package com.app.diario.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.diario.ui.components.SonoPrevia
import com.app.diario.ui.mock.RegistroSonoMock
import com.app.diario.ui.mock.registrosSonoMock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HigieneSonoIndividualScreen(
    registroSonoMock: RegistroSonoMock?,
    onVoltar: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalhes da avaliação",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                        )
                    }
                },
            )
        },
    ) { padding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
            contentAlignment = Alignment.TopCenter,
        ) {
            if (registroSonoMock == null) {
                Text(
                    text = "Registro não encontrado",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.Center),
                )
            } else {
                SonoPrevia(
                    registro = registroSonoMock,
                    modifier =
                        Modifier
                            .widthIn(max = 720.dp) // em telas grandes não estica demais
                            .fillMaxHeight()
                            .padding(16.dp),
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------

@Preview(showBackground = true)
@Composable
fun HigieneIndividualScreenPreview() {
    HigieneSonoIndividualScreen(
        registroSonoMock = registrosSonoMock.first(),
        onVoltar = {},
    )
}

@Preview(showBackground = true)
@Composable
fun HigieneIndividualScreenNotFoundPreview() {
    HigieneSonoIndividualScreen(
        registroSonoMock = null,
        onVoltar = {},
    )
}
