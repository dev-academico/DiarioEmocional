package com.app.diario.ui.avaliacao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.diario.dominio.repositorio.AvaliacaoRepository
import com.app.diario.ui.mock.AvaliacaoMock
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AvaliacaoViewModel(
    private val repository: AvaliacaoRepository,
) : ViewModel() {
    private val _estado = MutableStateFlow(AvaliacaoUiState())
    val estado: StateFlow<AvaliacaoUiState> = _estado.asStateFlow()

    init {
        carregarAvaliacoes()
    }

    fun carregarAvaliacoes() {
        _estado.update { it.copy(tela = EstadoAvaliacaoTela.Carregando) }
        viewModelScope.launch {
            try {
                delay(500)
                val lista = repository.getAvaliacoes()
                val novaTela =
                    if (lista.isEmpty()) {
                        EstadoAvaliacaoTela.Vazio
                    } else {
                        EstadoAvaliacaoTela.Sucesso(lista)
                    }
                _estado.update { it.copy(tela = novaTela) }
            } catch (e: Exception) {
                _estado.update { it.copy(tela = EstadoAvaliacaoTela.Erro("Não foi possível carregar as avaliações.")) }
            }
        }
    }

    fun adicionarAvaliacao(avaliacao: AvaliacaoMock) {
        repository.adicionarAvaliacao(avaliacao)
        val atual = _estado.value.tela
        if (atual is EstadoAvaliacaoTela.Sucesso) {
            val novaLista = repository.getAvaliacoes()
            _estado.update { it.copy(tela = EstadoAvaliacaoTela.Sucesso(novaLista)) }
        }
    }
}
