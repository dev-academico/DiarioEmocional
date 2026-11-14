package com.app.diario.ui.relatos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.diario.dominio.repositorio.RelatosRepository
import com.app.diario.ui.mock.RelatoMock
import com.app.diario.ui.mock.relatosMock
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RelatosViewModel(
    private val repository: RelatosRepository
) : ViewModel() {
    private val _estado = MutableStateFlow(RelatosUiState())
    val estado: StateFlow<RelatosUiState> = _estado.asStateFlow()

    init {
        carregarRelatos()
    }

    fun carregarRelatos() {
        _estado.update { it.copy(tela = EstadoTela.Carregando) }

        viewModelScope.launch {
            try {
                // Simulando atraso de rede (delay de 500ms para visualizar o carregando)
                delay(500)
                val lista = relatosMock

                val novaTela =
                    if (lista.isEmpty()) {
                        EstadoTela.Vazio
                    } else {
                        EstadoTela.Sucesso(lista)
                    }

                _estado.update { it.copy(tela = novaTela) }
            } catch (e: Exception) {
                _estado.update { it.copy(tela = EstadoTela.Erro("Não foi possível carregar os relatos.")) }
            }
        }
    }

    fun adicionarRelato(novoRelato: RelatoMock) {
        // Se estiver em sucesso, adiciona na lista
        val atual = _estado.value.tela
        if (atual is EstadoTela.Sucesso) {
            val novaLista = atual.relatos + novoRelato
            _estado.update { it.copy(tela = EstadoTela.Sucesso(novaLista)) }
        }
    }
}
