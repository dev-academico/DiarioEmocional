package com.app.diario.ui.pensamentos

import PensamentoMock
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pensamentosDisfuncionaisMock

class PensamentoViewModel : ViewModel() {
    private val _estado = MutableStateFlow(PensamentoUiState())
    val estado: StateFlow<PensamentoUiState> = _estado.asStateFlow()

    fun adicionarPensamento(pensamento: PensamentoMock) {
        _estado.update { current ->
            current.copy(pensamentos = current.pensamentos + pensamento)
        }
    }
}
