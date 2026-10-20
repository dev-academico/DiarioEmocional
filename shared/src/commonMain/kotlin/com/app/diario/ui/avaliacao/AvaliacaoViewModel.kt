package com.app.diario.ui.avaliacao

import AvaliacaoMock
import androidx.lifecycle.ViewModel
import avaliacaoMock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AvaliacaoViewModel : ViewModel() {
    private val _estado = MutableStateFlow(AvaliacaoUiState())
    val estado: StateFlow<AvaliacaoUiState> = _estado.asStateFlow()

    fun adicionarAvaliacao(avaliacao: AvaliacaoMock) {
        _estado.update { current ->
            current.copy(avaliacoes = current.avaliacoes + avaliacao)
        }
    }
}
