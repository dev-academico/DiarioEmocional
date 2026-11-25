package com.app.diario.domain.data.local

import com.app.diario.domain.model.DisposicaoAoAcordar
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.HigieneSono
import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.QualidadeSono
import com.app.diario.domain.model.repository.HigieneSonoRepository

class HigieneSonoRepositoryComCache(
    private val remoto: HigieneSonoRepository,
    private val cache: CacheDeHigieneSono,
) : HigieneSonoRepository {
    override suspend fun buscarHigieneSono(): Listagem<HigieneSono> =
        try {
            remoto.buscarHigieneSono().also { cache.salvar(it.itens) }
        } catch (e: Falha.SemConexao) {
            val guardados = cache.ler() ?: throw e
            Listagem(guardados, doCache = true)
        }

    override suspend fun criarHigieneSono(
        dataRegistro: String,
        horarioDormiu: String,
        horarioAcordou: String,
        qualidade: QualidadeSono,
        disposicao: DisposicaoAoAcordar,
        usouCelularAntesDeDormir: Boolean,
        tevePesadelos: Boolean,
        comentarioSonhos: String,
    ): HigieneSono =
        remoto.criarHigieneSono(
            dataRegistro = dataRegistro,
            horarioDormiu = horarioDormiu,
            horarioAcordou = horarioAcordou,
            qualidade = qualidade,
            disposicao = disposicao,
            usouCelularAntesDeDormir = usouCelularAntesDeDormir,
            tevePesadelos = tevePesadelos,
            comentarioSonhos = comentarioSonhos,
        )
}
