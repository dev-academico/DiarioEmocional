import androidx.compose.runtime.Composable
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowWidthSizeClass
import com.app.diario.ui.layout.PensamentoLayoutCelular
import com.app.diario.ui.layout.PensamentoLayoutLargo


@Composable
fun RegistroPensamentoScreen(
    pensamentos : List<PensamentoMock>,
    onPensamentoClick: (PensamentoMock) -> Unit,
    onVoltar : () -> Unit
) {

    val windowSizeClass =
        currentWindowAdaptiveInfo().windowSizeClass

    when (windowSizeClass.windowWidthSizeClass) {

        WindowWidthSizeClass.COMPACT -> {
            PensamentoLayoutCelular(pensamentos = pensamentos, onVoltar = onVoltar)
        }

        WindowWidthSizeClass.MEDIUM,
        WindowWidthSizeClass.EXPANDED -> {
            PensamentoLayoutLargo(pensamentos = pensamentos, onVoltar = onVoltar)
        }
    }
}

@Preview
@Composable
fun PensamentosScreenPreview() {
    RegistroPensamentoScreen(
        pensamentos = pensamentosDisfuncionaisMock,
        onPensamentoClick = {},
        onVoltar = {}
    )
}

@Preview
@Composable
fun PensamentosScreenEmptyPreview() {
    RegistroPensamentoScreen(
        pensamentos = emptyList(),
        onPensamentoClick = {},
        onVoltar = {}
    )
}