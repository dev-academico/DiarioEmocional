package com.app.diario.ui.pensamentos

import PensamentoMock
import pensamentosDisfuncionaisMock

data class PensamentoUiState(
    val pensamentos: List<PensamentoMock> = pensamentosDisfuncionaisMock,
    val isLoading: Boolean = false,
)
