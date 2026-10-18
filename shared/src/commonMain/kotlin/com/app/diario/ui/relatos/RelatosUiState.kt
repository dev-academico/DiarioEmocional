package com.app.diario.ui.relatos

import com.app.diario.ui.mock.RelatoMock

data class RelatosUiState(
    val relatos: List<RelatoMock> = emptyList(),
    val isLoading: Boolean = false
)