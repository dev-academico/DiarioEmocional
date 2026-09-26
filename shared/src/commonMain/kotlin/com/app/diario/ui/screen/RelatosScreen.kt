import androidx.compose.runtime.Composable
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowWidthSizeClass
import com.app.diario.ui.layout.RelatosLayoutCelular
import com.app.diario.ui.layout.RelatosLayoutLargo
import com.app.diario.ui.mock.RelatoMock
import com.app.diario.ui.mock.relatosMock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun RelatosScreen(
    relatos: List<RelatoMock>,
    onRelatoIndividual: (RelatoMock) -> Unit,
    onNovoRelatoClick: () -> Unit,
    onVoltar: () -> Unit
) {
    var relatoSelecionado by remember {
        mutableStateOf<RelatoMock?>(null)
    }

    val width =
        currentWindowAdaptiveInfo()
            .windowSizeClass
            .windowWidthSizeClass

    when (width) {

        WindowWidthSizeClass.COMPACT -> {
            RelatosLayoutCelular(
                relatos = relatos,
                onRelatoClick = onRelatoIndividual,
                onVoltar = onVoltar,
                onNovoRelatoClick =  onNovoRelatoClick
            )
        }

        WindowWidthSizeClass.MEDIUM,
        WindowWidthSizeClass.EXPANDED -> {
            RelatosLayoutLargo(
                relatos = relatos,
                relatoSelecionado = relatoSelecionado,
                onRelatoClick = {
                    relatoSelecionado = it
                },
                onVoltar = onVoltar,
                onNovoRelatoClick = onNovoRelatoClick
            )
        }
    }
}