package com.app.diario.dominio.repositorio

import com.app.diario.ui.mock.RegistroSonoMock

interface HigieneSonoRepository {
    fun getRegistros(): List<RegistroSonoMock>
    fun adicionarRegistro(registro: RegistroSonoMock)
}
