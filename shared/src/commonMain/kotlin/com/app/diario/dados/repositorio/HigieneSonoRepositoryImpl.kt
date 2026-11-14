package com.app.diario.dados.repositorio

import com.app.diario.dominio.repositorio.HigieneSonoRepository
import com.app.diario.ui.mock.RegistroSonoMock
import com.app.diario.ui.mock.registrosSonoMock

class HigieneSonoRepositoryImpl : HigieneSonoRepository {
    private val listaRegistros = registrosSonoMock.toMutableList()

    override fun getRegistros(): List<RegistroSonoMock> {
        return listaRegistros
    }

    override fun adicionarRegistro(registro: RegistroSonoMock) {
        listaRegistros.add(0, registro)
    }
}
