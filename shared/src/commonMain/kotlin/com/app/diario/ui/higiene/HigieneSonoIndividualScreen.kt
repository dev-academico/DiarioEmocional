package com.app.diario.ui.higiene

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.diario.domain.model.DisposicaoAoAcordar
import com.app.diario.domain.model.HigieneSono
import com.app.diario.domain.model.QualidadeSono
import com.app.diario.ui.components.SonoPrevia

@Composable
fun HigieneSonoIndividualScreen(
    viewModel: HigieneSonoViewModel = viewModel { HigieneSonoViewModel() },
    id: String,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val registro =
        when (val tela = estado.tela) {
            is EstadoSonoTela.Sucesso -> tela.registros.find { it.id == id }
            else -> null
        }

    HigieneSonoIndividualContent(
        registro = registro,
        onVoltar = onVoltar,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HigieneSonoIndividualContent(
    registro: HigieneSono?,
    onVoltar: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalhes da higiene do sono",
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
            if (registro == null) {
                Text(
                    text = "Registro não encontrado",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.Center),
                )
            } else {
                SonoPrevia(
                    registro = registro,
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
    val exemplo = HigieneSono(
        id = "1",
        dataRegistro = "2026-10-15",
        horarioDormiu = "23:30",
        horarioAcordou = "06:45",
        qualidade = QualidadeSono.REGULAR,
        disposicao = DisposicaoAoAcordar.MEDIA,
        usouCelularAntesDeDormir = true,
        tevePesadelos = false,
        comentarioSonhos = "Acordei algumas vezes durante a madrugada."
    )
    HigieneSonoIndividualContent(
        registro = exemplo,
        onVoltar = {},
    )
}

@Preview(showBackground = true)
@Composable
fun HigieneIndividualScreenNotFoundPreview() {
    HigieneSonoIndividualContent(
        registro = null,
        onVoltar = {},
    )
}
