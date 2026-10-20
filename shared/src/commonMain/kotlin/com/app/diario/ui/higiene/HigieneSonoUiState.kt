package com.app.diario.ui.higiene

import com.app.diario.ui.mock.RegistroSonoMock
import com.app.diario.ui.mock.registrosSonoMock

data class HigieneSonoUiState(
    val registros: List<RegistroSonoMock> = registrosSonoMock,
    val isLoading: Boolean = false,
)
