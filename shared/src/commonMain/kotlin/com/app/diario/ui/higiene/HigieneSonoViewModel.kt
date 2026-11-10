package com.app.diario.ui.higiene

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.diario.ui.mock.RegistroSonoMock
import com.app.diario.ui.mock.registrosSonoMock
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HigieneSonoViewModel : ViewModel() {
    private val _estado = MutableStateFlow(HigieneSonoUiState())
    val estado: StateFlow<HigieneSonoUiState> = _estado.asStateFlow()

    init {
        carregarRegistros()
    }

    fun carregarRegistros() {
        _estado.update { it.copy(tela = EstadoSonoTela.Carregando) }
        viewModelScope.launch {
            try {
                delay(500)
                val lista = registrosSonoMock
                val novaTela =
                    if (lista.isEmpty()) {
                        EstadoSonoTela.Vazio
                    } else {
                        EstadoSonoTela.Sucesso(lista)
                    }
                _estado.update { it.copy(tela = novaTela) }
            } catch (e: Exception) {
                _estado.update { it.copy(tela = EstadoSonoTela.Erro("Não foi possível carregar os registros de sono.")) }
            }
        }
    }

    fun adicionarRegistro(registro: RegistroSonoMock) {
        val atual = _estado.value.tela
        if (atual is EstadoSonoTela.Sucesso) {
            val novaLista = atual.registros + registro
            _estado.update { it.copy(tela = EstadoSonoTela.Sucesso(novaLista)) }
        }
    }
}
