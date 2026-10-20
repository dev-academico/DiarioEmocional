package com.app.diario.ui.avaliacao

import AvaliacaoMock
import avaliacaoMock

data class AvaliacaoUiState(
    val avaliacoes: List<AvaliacaoMock> = avaliacaoMock,
    val isLoading: Boolean = false,
)
