import androidx.lifecycle.ViewModel
import com.app.diario.ui.mock.RelatoMock
import com.app.diario.ui.relatos.RelatosUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RelatosViewModel : ViewModel() {
    private val _estado = MutableStateFlow(RelatosUiState()) //so viewModel altera
    val estado: StateFlow<RelatosUiState> = _estado.asStateFlow() // a tela so le

    fun adicionarRelato(novoRelato: RelatoMock){
        _estado.update { current ->
            current.copy(relatos = current.relatos + novoRelato)
        }
    }
}