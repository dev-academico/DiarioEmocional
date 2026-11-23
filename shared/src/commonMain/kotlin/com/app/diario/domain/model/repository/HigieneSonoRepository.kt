package com.app.diario.domain.model.repository

import com.app.diario.domain.model.DisposicaoAoAcordar
import com.app.diario.domain.model.HigieneSono
import com.app.diario.domain.model.QualidadeSono

interface HigieneSonoRepository {
    suspend fun buscarHigieneSono(): List<HigieneSono>
    suspend fun criarHigieneSono(dataRegistro: String, // "2026-10-15"
                                 horarioDormiu: String, // "HH:mm"
                                 horarioAcordou: String, // "HH:mm"
                                 qualidade: QualidadeSono,
                                 disposicao: DisposicaoAoAcordar,
                                 usouCelularAntesDeDormir: Boolean,
                                 tevePesadelos: Boolean,
                                 comentarioSonhos: String): HigieneSono
}