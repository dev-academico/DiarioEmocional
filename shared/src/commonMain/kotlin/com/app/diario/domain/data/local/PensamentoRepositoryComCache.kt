package com.app.diario.domain.data.local

import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.Pensamento
import com.app.diario.domain.model.repository.PensamentoRepository

class PensamentoRepositoryComCache(
    private val remoto: PensamentoRepository,
    private val cache: CacheDePensamentos,
) : PensamentoRepository {
    override suspend fun buscarPensamento(): Listagem<Pensamento> =
        try {
            remoto.buscarPensamento().also { cache.salvar(it.itens) }
        } catch (e: Falha.SemConexao) {
            val guardados = cache.ler() ?: throw e
            Listagem(guardados, doCache = true)
        }

    override suspend fun criarPensamento(
        dataRegistro: String,
        situacao: String,
        pensamentosImagens: String,
        emocoesSentimentos: String,
        comportamentosReacoes: String,
    ): Pensamento =
        remoto.criarPensamento(
            dataRegistro = dataRegistro,
            situacao = situacao,
            pensamentosImagens = pensamentosImagens,
            emocoesSentimentos = emocoesSentimentos,
            comportamentosReacoes = comportamentosReacoes,
        )
}
