package com.app.diario.ui.relatos

import com.app.diario.ui.mock.RelatoMock
import com.app.diario.ui.mock.relatosMock

data class RelatosUiState(
    val relatos: List<RelatoMock> = relatosMock,
    val isLoading: Boolean = false
)