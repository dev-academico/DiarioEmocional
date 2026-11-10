package com.app.diario.ui.pensamentos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.diario.ui.mock.PensamentoMock
import com.app.diario.ui.mock.pensamentosDisfuncionaisMock
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PensamentoViewModel : ViewModel() {
    private val _estado = MutableStateFlow(PensamentoUiState())
    val estado: StateFlow<PensamentoUiState> = _estado.asStateFlow()

    init {
        carregarPensamentos()
    }

    fun carregarPensamentos() {
        _estado.update { it.copy(tela = EstadoPensamentoTela.Carregando) }
        viewModelScope.launch {
            try {
                delay(500)
                val lista = pensamentosDisfuncionaisMock
                val novaTela =
                    if (lista.isEmpty()) {
                        EstadoPensamentoTela.Vazio
                    } else {
                        EstadoPensamentoTela.Sucesso(lista)
                    }
                _estado.update { it.copy(tela = novaTela) }
            } catch (e: Exception) {
                _estado.update { it.copy(tela = EstadoPensamentoTela.Erro("Não foi possível carregar os pensamentos.")) }
            }
        }
    }

    fun adicionarPensamento(pensamento: PensamentoMock) {
        val atual = _estado.value.tela
        if (atual is EstadoPensamentoTela.Sucesso) {
            val novaLista = atual.pensamentos + pensamento
            _estado.update { it.copy(tela = EstadoPensamentoTela.Sucesso(novaLista)) }
        }
    }
}
