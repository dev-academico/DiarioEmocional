package com.app.diario

import com.app.diario.dados.repositorio.AvaliacaoRepositoryImpl
import com.app.diario.dados.repositorio.HigieneSonoRepositoryImpl
import com.app.diario.dados.repositorio.PensamentoRepositoryImpl
import com.app.diario.dados.repositorio.RelatosRepositoryImpl
import com.app.diario.ui.avaliacao.AvaliacaoViewModel
import com.app.diario.ui.higiene.HigieneSonoViewModel
import com.app.diario.ui.pensamentos.PensamentoViewModel
import com.app.diario.ui.relatos.RelatosViewModel
import kotlin.test.Test
import kotlin.test.assertNotNull

class InjecaoDependenciaTest {
    @Test
    fun testMontarModulos() {
        assertNotNull(RelatosViewModel(RelatosRepositoryImpl()))
        assertNotNull(PensamentoViewModel(PensamentoRepositoryImpl()))
        assertNotNull(HigieneSonoViewModel(HigieneSonoRepositoryImpl()))
        assertNotNull(AvaliacaoViewModel(AvaliacaoRepositoryImpl()))
    }
}
