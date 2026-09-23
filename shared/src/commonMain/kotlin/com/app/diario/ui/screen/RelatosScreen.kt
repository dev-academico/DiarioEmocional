import androidx.compose.runtime.Composable
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowWidthSizeClass
import com.app.diario.ui.layout.RelatosLayoutCelular
import com.app.diario.ui.layout.RelatosLayoutLargo
import com.app.diario.ui.mock.RelatoMock
import com.app.diario.ui.mock.relatosMock


@Composable
fun RelatosScreen(
    relatos : List<RelatoMock>,
    onRelatoClick: (RelatoMock) -> Unit,
    onVoltar : () -> Unit
) {

    val windowSizeClass =
        currentWindowAdaptiveInfo().windowSizeClass

    when (windowSizeClass.windowWidthSizeClass) {

        WindowWidthSizeClass.COMPACT -> {
            RelatosLayoutCelular(relatos = relatos)
        }

        WindowWidthSizeClass.MEDIUM,
        WindowWidthSizeClass.EXPANDED -> {
            RelatosLayoutLargo(relatos = relatos)
        }
    }
}

@Preview
@Composable
fun RelatosScreenPreview() {
    RelatosScreen(
        relatos = relatosMock,
        onRelatoClick = {},
        onVoltar = {}
    )
}

@Preview
@Composable
fun RelatosScreenEmptyPreview() {
    RelatosScreen(
        relatos = emptyList(),
        onRelatoClick = {},
        onVoltar = {}
    )
}