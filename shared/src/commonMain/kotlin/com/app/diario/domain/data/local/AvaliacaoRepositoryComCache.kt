package com.app.diario.domain.data.local

import com.app.diario.domain.model.Avaliacao
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.repository.AvaliacaoRepository

class AvaliacaoRepositoryComCache(
    private val remoto: AvaliacaoRepository,
    private val cache: CacheDeAvaliacoes,
) : AvaliacaoRepository {
    override suspend fun buscarAvaliacoes(): Listagem<Avaliacao> =
        try {
            remoto.buscarAvaliacoes().also { cache.salvar(it.itens) }
        } catch (e: Falha.SemConexao) {
            val guardadas = cache.ler() ?: throw e
            Listagem(guardadas, doCache = true)
        }

    override suspend fun criarAvaliacao(
        dataRegistro: String,
        intensidade: Int,
        descricao: String,
        gatilhos: List<String>,
    ): Avaliacao =
        remoto.criarAvaliacao(
            dataRegistro = dataRegistro,
            intensidade = intensidade,
            descricao = descricao,
            gatilhos = gatilhos,
        )
}
