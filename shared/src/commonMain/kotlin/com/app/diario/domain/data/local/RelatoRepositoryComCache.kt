package com.app.diario.domain.data.local

import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.Relato
import com.app.diario.domain.model.repository.RelatoRepository

class RelatoRepositoryComCache(
    private val remoto: RelatoRepository,
    private val cache: CacheDeRelatos,
) : RelatoRepository {
    override suspend fun buscarRelatos(): Listagem<Relato> =
        try {
            remoto.buscarRelatos().also { cache.salvar(it.itens) }
        } catch (e: Falha.SemConexao) {
            val guardados = cache.ler() ?: throw e
            Listagem(guardados, doCache = true)
        }

    override suspend fun criarRelato(
        titulo: String,
        dataRegistro: String,
        conteudo: String,
    ): Relato = remoto.criarRelato(titulo, dataRegistro, conteudo)
}
