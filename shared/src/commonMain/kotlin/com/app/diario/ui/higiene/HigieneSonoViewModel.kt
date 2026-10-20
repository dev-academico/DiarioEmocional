package com.app.diario.ui.higiene

import androidx.lifecycle.ViewModel
import com.app.diario.ui.mock.RegistroSonoMock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HigieneSonoViewModel : ViewModel() {
    private val _estado = MutableStateFlow(HigieneSonoUiState())
    val estado: StateFlow<HigieneSonoUiState> = _estado.asStateFlow()

    fun adicionarRegistro(registro: RegistroSonoMock) {
        _estado.update { current ->
            current.copy(registros = current.registros + registro)
        }
    }
}
