import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowWidthSizeClass
import com.app.diario.ui.layout.PensamentoLayoutCelular
import com.app.diario.ui.layout.PensamentoLayoutLargo

@Composable
fun RegistroPensamentoScreen(
    pensamentos: List<PensamentoMock>,
    onPensamentoIndividual: (PensamentoMock) -> Unit,
    onNovoPensamentoClick: () -> Unit,
    onVoltar: () -> Unit
) {
    var pensamentoSelecionado by remember {
        mutableStateOf<PensamentoMock?>(null)
    }

    val width =
        currentWindowAdaptiveInfo()
            .windowSizeClass
            .windowWidthSizeClass

    when (width) {

        WindowWidthSizeClass.COMPACT -> {
            PensamentoLayoutCelular(
                pensamentos = pensamentos,
                onPensamentoClick = onPensamentoIndividual,
                onVoltar = onVoltar,
                onNovoPensamentoClick = onNovoPensamentoClick
            )
        }

        WindowWidthSizeClass.MEDIUM,
        WindowWidthSizeClass.EXPANDED -> {
            PensamentoLayoutLargo(
                pensamentos = pensamentos,
                pensamentoSelecionado = pensamentoSelecionado,
                onPensamentoClick = {
                    pensamentoSelecionado = it
                },
                onVoltar = onVoltar,
                onNovoPensamentoClick = onNovoPensamentoClick
            )
        }
    }
}

@Preview
@Composable
fun RegistroPensamentoScreenPreview() {
    RegistroPensamentoScreen(
        pensamentos = pensamentosDisfuncionaisMock,
        onPensamentoIndividual = {},
        onNovoPensamentoClick = {},
        onVoltar = {}
    )
}

@Preview
@Composable
fun RegistroPensamentoScreenEmptyPreview() {
    RegistroPensamentoScreen(
        pensamentos = emptyList(),
        onPensamentoIndividual = {},
        onNovoPensamentoClick = {},
        onVoltar = {}
    )
}